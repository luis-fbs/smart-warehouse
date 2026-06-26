package ps.smartwarehouse.domain.alocador;

import ps.smartwarehouse.domain.Produto;
import ps.smartwarehouse.domain.Corredor;
import ps.smartwarehouse.domain.Galpao;
import ps.smartwarehouse.domain.Prateleira;
import ps.smartwarehouse.domain.Slot;
import ps.smartwarehouse.domain.Zona;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class GiroRapido implements AlocadorStrategy {

    @Override
    public Map<Produto, Slot> alocar(List<Produto> produtos, Galpao galpao) {
        System.out.println("\n  >> ESTRATEGIA: Giro Rapido (FIFO)");
        System.out.println("     Ordenando por validade (crescente) e indice de saida (decrescente)...");

        // Produtos com validade mais curta ou maior rotatividade saem primeiro
        List<Produto> ordenados = new ArrayList<>(produtos);
        ordenados.sort(Comparator
                .comparing(Produto::getValidade)
                .thenComparingDouble(p -> -p.getIndiceSaida()));

        List<Slot> disponiveis = coletarSlotsDisponiveis(galpao);
        Map<Produto, Slot> resultado = new LinkedHashMap<>();
        Iterator<Slot> itSlot = disponiveis.iterator();

        for (Produto p : ordenados) {
            if (!itSlot.hasNext()) {
                System.out.printf("     [AVISO] Sem slots disponiveis para: %s%n", p.getNome());
                continue;
            }
            Slot slot = itSlot.next();
            slot.adicionarProduto(p);
            resultado.put(p, slot);
            System.out.printf("     [+] %-22s -> %-12s  (val: %s | saida: %.2f)%n",
                    p.getNome(), slot.getIdentificador(), p.getValidade(), p.getIndiceSaida());
        }
        return resultado;
    }

    /** Retorna slots não-cheios de zonas não-bloqueadas, preservando a ordem física. */
    private List<Slot> coletarSlotsDisponiveis(Galpao galpao) {
        List<Slot> slots = new ArrayList<>();
        for (Zona z : galpao.getZonas()) {
            if (z.isEstaBloqueada()) continue;
            for (Corredor c : z.getCorredores())
                for (Prateleira p : c.getPrateleiras())
                    for (Slot s : p.getSlots())
                        if (!s.isEstaCheio()) slots.add(s);
        }
        return slots;
    }
}