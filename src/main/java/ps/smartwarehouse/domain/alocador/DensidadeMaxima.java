package ps.smartwarehouse.domain.alocador;

import ps.smartwarehouse.domain.Produto;
import ps.smartwarehouse.domain.Corredor;
import ps.smartwarehouse.domain.Galpao;
import ps.smartwarehouse.domain.Prateleira;
import ps.smartwarehouse.domain.Slot;
import ps.smartwarehouse.domain.Zona;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DensidadeMaxima implements AlocadorStrategy {

    @Override
    public Map<Produto, Slot> alocar(List<Produto> produtos, Galpao galpao) {
        System.out.println("\n  >> ESTRATEGIA: Densidade Maxima (Best-Fit)");
        System.out.println("     Minimizando espacos ociosos sem considerar categoria ou risco...");

        Map<Produto, Slot> resultado = new LinkedHashMap<>();

        for (Produto p : produtos) {
            // A cada produto, reavalia os slots (o estado muda conforme alocamos)
            Optional<Slot> melhor = coletarSlotsDisponiveis(galpao).stream()
                    .filter(s -> s.getEspacoDisponivel() > 0)
                    .min(Comparator.comparingInt(Slot::getEspacoDisponivel));

            if (melhor.isPresent()) {
                Slot slot = melhor.get();
                slot.adicionarProduto(p);
                resultado.put(p, slot);
                System.out.printf("     [+] %-22s -> %-12s  (espaco restante: %d)%n",
                        p.getNome(), slot.getIdentificador(), slot.getEspacoDisponivel());
            } else {
                System.out.printf("     [X] %-22s – armazem cheio!%n", p.getNome());
            }
        }
        return resultado;
    }

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
