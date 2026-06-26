package ps.smartwarehouse.operacoes.strategy;

import ps.smartwarehouse.inventario.domain.Galpao;
import ps.smartwarehouse.inventario.domain.Produto;

import java.util.Comparator;
import java.util.Optional;


public class GiroRapido implements AlocarStrategy {

    @Override
    public String nome() { return "GIRO_RAPIDO"; }

    @Override
    public Optional<SlotCandidato> alocar(Produto produto, Galpao galpao) {
        return candidatos(galpao).stream()
                .min(Comparator
                        .comparingInt((SlotCandidato c) -> c.slot().getNivel())
                        .thenComparingInt(c -> c.zona().getDistanciaDoca()));
    }
}
