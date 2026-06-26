package ps.smartwarehouse.inventario;

import org.springframework.stereotype.Service;
import ps.smartwarehouse.comum.EventoLog;
import ps.smartwarehouse.inventario.domain.Corredor;
import ps.smartwarehouse.inventario.domain.Galpao;
import ps.smartwarehouse.inventario.domain.Iterador;
import ps.smartwarehouse.inventario.domain.Prateleira;
import ps.smartwarehouse.inventario.domain.Produto;
import ps.smartwarehouse.inventario.domain.Slot;
import ps.smartwarehouse.inventario.domain.Zona;
import ps.smartwarehouse.inventario.sensor.Observer;
import ps.smartwarehouse.inventario.sensor.Sensor;
import ps.smartwarehouse.inventario.sensor.SistemaAlerta;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class InventarioService {

    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Galpao galpao = new Galpao("GALPAO-CENTRAL");
    private final EventoLog log;
    private final SistemaAlerta sistemaAlerta;

    public InventarioService(EventoLog log) {
        this.log = log;
        this.sistemaAlerta = new SistemaAlerta(log);
    }

    public Galpao getGalpao() { return galpao; }

    public SistemaAlerta getSistemaAlerta() { return sistemaAlerta; }


    public List<Map<String, Object>> auditoria() {
        List<Map<String, Object>> itens = new ArrayList<>();
        Iterador<Produto> it = galpao.iteradorDeProdutos();
        int ordem = 1;
        while (it.temProximo()) {
            Produto p = it.proximo();
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("ordem", ordem++);
            item.put("nome", p.getNome());
            item.put("categoria", p.getCategoria().name());
            item.put("validade", p.getValidade().format(DATA));
            item.put("risco", p.getRisco());
            itens.add(item);
        }
        log.registrar("AUDITORIA concluída: " + (ordem - 1) + " produto(s) varridos via Iterator.");
        return itens;
    }


    public List<Sensor> sensores() {
        List<Sensor> todos = new ArrayList<>();
        for (Zona z : galpao.getZonas()) todos.addAll(z.getSensores());
        return todos;
    }

    public Optional<Sensor> buscarSensor(String zonaId, String tipo) {
        return galpao.getZonas().stream()
                .filter(z -> z.getIdentificador().equalsIgnoreCase(zonaId))
                .flatMap(z -> z.getSensores().stream())
                .filter(s -> s.getTipo().equalsIgnoreCase(tipo))
                .findFirst();
    }

    public Sensor registrarLeitura(String zonaId, String tipo, double valor) {
        Sensor sensor = buscarSensor(zonaId, tipo)
                .orElseThrow(() -> new IllegalArgumentException("Sensor não encontrado: " + tipo + " na zona " + zonaId));
        sensor.leitura(valor);
        return sensor;
    }

    public void registrarObservadorGlobal(Observer observer) {
        sensores().forEach(s -> s.registerObserver(observer));
    }


    public boolean armazenar(Zona zona, Slot slot, Produto produto) {
        if (zona.zonaEstaBloqueada()) return false;
        return slot.armazenar(produto);
    }

    public Zona buscarZona(String zonaId) {
        return galpao.getZonas().stream()
                .filter(z -> z.getIdentificador().equalsIgnoreCase(zonaId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Zona não encontrada: " + zonaId));
    }

    public void desbloquearZona(String zonaId) {
        buscarZona(zonaId).desbloquear();
        log.registrar("Zona " + zonaId + " DESBLOQUEADA: movimentações liberadas.");
    }


    public List<Map<String, Object>> mapa() {
        List<Map<String, Object>> zonas = new ArrayList<>();
        for (Zona z : galpao.getZonas()) {
            Map<String, Object> mz = new LinkedHashMap<>();
            mz.put("zona", z.getIdentificador());
            mz.put("bloqueada", z.zonaEstaBloqueada());
            mz.put("distanciaDoca", z.getDistanciaDoca());
            int ocupados = 0, totalSlots = 0;
            for (Corredor c : z.getCorredores())
                for (Prateleira p : c.getPrateleiras())
                    for (Slot s : p.getSlots()) {
                        totalSlots++;
                        ocupados += s.getProdutos().size();
                    }
            mz.put("produtosArmazenados", ocupados);
            mz.put("slots", totalSlots);
            List<String> tiposSensor = z.getSensores().stream().map(Sensor::getTipo).toList();
            mz.put("sensores", tiposSensor);
            zonas.add(mz);
        }
        return zonas;
    }
}
