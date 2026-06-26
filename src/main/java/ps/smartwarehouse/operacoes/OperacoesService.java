package ps.smartwarehouse.operacoes;

import org.springframework.stereotype.Service;
import ps.smartwarehouse.comum.EventoLog;
import ps.smartwarehouse.inventario.InventarioService;
import ps.smartwarehouse.inventario.domain.Galpao;
import ps.smartwarehouse.inventario.domain.Produto;
import ps.smartwarehouse.inventario.sensor.Observer;
import ps.smartwarehouse.inventario.sensor.Sensor;
import ps.smartwarehouse.operacoes.robo.ModuloBrigada;
import ps.smartwarehouse.operacoes.robo.Robo;
import ps.smartwarehouse.operacoes.strategy.Alocador;
import ps.smartwarehouse.operacoes.strategy.AlocarStrategy;
import ps.smartwarehouse.operacoes.strategy.ControleAmbiental;
import ps.smartwarehouse.operacoes.strategy.DensidadeMaxima;
import ps.smartwarehouse.operacoes.strategy.GiroRapido;
import ps.smartwarehouse.operacoes.strategy.SlotCandidato;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class OperacoesService implements Observer {

    private final InventarioService inventario;
    private final ModuloBrigada brigada;
    private final EventoLog log;

    private final Map<String, AlocarStrategy> estrategias = new LinkedHashMap<>();
    private final Alocador alocador;
    private final List<Robo> robos = new ArrayList<>();

    public OperacoesService(InventarioService inventario, ModuloBrigada brigada, EventoLog log) {
        this.inventario = inventario;
        this.brigada = brigada;
        this.log = log;
        for (AlocarStrategy s : List.of(new GiroRapido(), new ControleAmbiental(), new DensidadeMaxima())) {
            estrategias.put(s.nome(), s);
        }
        this.alocador = new Alocador(estrategias.get("GIRO_RAPIDO"));
    }

    public List<String> estrategiasDisponiveis() {
        return new ArrayList<>(estrategias.keySet());
    }

    public List<Robo> getRobos() { return robos; }

    public void adicionarRobo(Robo robo) { robos.add(robo); }


    public List<Map<String, Object>> receberLote(List<Produto> lote, String estrategiaNome) {
        AlocarStrategy estrategia = estrategias.get(estrategiaNome);
        if (estrategia == null) throw new IllegalArgumentException("Estratégia inválida: " + estrategiaNome);
        alocador.setEstrategia(estrategia);
        log.registrar("DOCA: lote de " + lote.size() + " produto(s) recebido. Estratégia = " + estrategiaNome);

        Galpao galpao = inventario.getGalpao();
        List<Map<String, Object>> relatorio = new ArrayList<>();
        for (Produto produto : lote) {
            Map<String, Object> linha = new LinkedHashMap<>();
            linha.put("produto", produto.getNome());
            linha.put("categoria", produto.getCategoria().name());

            Optional<SlotCandidato> destino = alocador.alocar(produto, galpao);
            if (destino.isPresent()) {
                SlotCandidato c = destino.get();
                boolean ok = inventario.armazenar(c.zona(), c.slot(), produto);
                if (ok) {
                    String local = "Zona " + c.zona().getIdentificador() + " / Slot " + c.slot().getIdentificador()
                            + " (nível " + c.slot().getNivel() + ")";
                    linha.put("status", "ALOCADO");
                    linha.put("destino", local);
                    log.registrar(produto.getNome() + " -> " + local + " [" + estrategiaNome + "]");
                } else {
                    linha.put("status", "REJEITADO");
                    linha.put("destino", "zona bloqueada");
                }
            } else {
                linha.put("status", "REJEITADO");
                linha.put("destino", "sem slot compatível disponível");
                log.registrar(produto.getNome() + " não pôde ser alocado [" + estrategiaNome + "]");
            }
            relatorio.add(linha);
        }
        return relatorio;
    }
    
    @Override
    public void update(Sensor sensor) {
        if (sensor.getZona() == null) return;
        String zonaId = sensor.getZona().getIdentificador();
        sensor.getZona().bloquear();
        log.registrar("OPERAÇÕES: novas ordens de movimentação para a Zona " + zonaId + " BLOQUEADAS.");
        brigada.acionar(zonaId, robos);
    }
}
