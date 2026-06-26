package ps.smartwarehouse.operacoes.strategy;

import ps.smartwarehouse.inventario.domain.Corredor;
import ps.smartwarehouse.inventario.domain.Galpao;
import ps.smartwarehouse.inventario.domain.Prateleira;
import ps.smartwarehouse.inventario.domain.Produto;
import ps.smartwarehouse.inventario.domain.Slot;
import ps.smartwarehouse.inventario.domain.Zona;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface AlocarStrategy {

    String nome();

    Optional<SlotCandidato> alocar(Produto produto, Galpao galpao);

    default List<SlotCandidato> candidatos(Galpao galpao) {
        List<SlotCandidato> livres = new ArrayList<>();
        for (Zona zona : galpao.getZonas()) {
            if (zona.zonaEstaBloqueada()) continue;
            for (Corredor corredor : zona.getCorredores()) {
                for (Prateleira prateleira : corredor.getPrateleiras()) {
                    for (Slot slot : prateleira.getSlots()) {
                        if (!slot.estaCheio()) livres.add(new SlotCandidato(zona, slot));
                    }
                }
            }
        }
        return livres;
    }
}
