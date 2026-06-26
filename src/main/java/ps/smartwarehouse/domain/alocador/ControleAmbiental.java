package ps.smartwarehouse.domain.alocador;
import ps.smartwarehouse.domain.Produto;
import ps.smartwarehouse.domain.Corredor;
import ps.smartwarehouse.domain.Galpao;
import ps.smartwarehouse.domain.Prateleira;
import ps.smartwarehouse.domain.Produto;
import ps.smartwarehouse.domain.Slot;
import ps.smartwarehouse.domain.Zona;
import ps.smartwarehouse.domain.sensor.Sensor;

import java.util.*;

public class ControleAmbiental implements AlocadorStrategy {

    private static final int RISCO_MINIMO_AMBIENTE_CONTROLADO = 3;

    @Override
    public Map<Produto, Slot> alocar(List<Produto> produtos, Galpao galpao) {
        System.out.println("\n  >> ESTRATEGIA: Controle Ambiental");
        System.out.println("     Buscando zonas com sensores compativeis ao nivel de risco...");

        Map<Produto, Slot> resultado = new LinkedHashMap<>();

        for (Produto p : produtos) {
            Slot slot = buscarSlotCompativel(p, galpao);
            if (slot != null) {
                slot.adicionarProduto(p);
                resultado.put(p, slot);
                System.out.printf("     [+] %-22s (risco %d) -> %-12s%n",
                        p.getNome(), p.getRisco(), slot.getIdentificador());
            } else {
                System.out.printf("     [X] %-22s (risco %d) – nenhuma zona compativel disponivel!%n",
                        p.getNome(), p.getRisco());
            }
        }
        return resultado;
    }

    private Slot buscarSlotCompativel(Produto produto, Galpao galpao) {
        for (Zona zona : galpao.getZonas()) {
            if (zona.isEstaBloqueada()) continue;
            if (!zonaEhCompativel(zona, produto.getRisco())) continue;

            for (Corredor c : zona.getCorredores())
                for (Prateleira p : c.getPrateleiras())
                    for (Slot s : p.getSlots())
                        if (!s.isEstaCheio()) return s;
        }
        return null;
    }

    /**
     * Risco >= 3 exige sensores de TEMPERATURA e UMIDADE na zona.
     * Risco < 3 aceita qualquer zona disponível.
     */
    private boolean zonaEhCompativel(Zona zona, int risco) {
        if (risco < RISCO_MINIMO_AMBIENTE_CONTROLADO) return true;

        List<Sensor> sensores = zona.getSensores();
        boolean temTemperatura = sensores.stream()
                .anyMatch(s -> s.getTipo().equals("TEMPERATURA"));
        boolean temUmidade = sensores.stream()
                .anyMatch(s -> s.getTipo().equals("UMIDADE"));

        return temTemperatura && temUmidade;
    }
}
