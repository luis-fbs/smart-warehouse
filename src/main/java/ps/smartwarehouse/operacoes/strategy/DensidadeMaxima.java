package ps.smartwarehouse.operacoes.strategy;

import ps.smartwarehouse.inventario.domain.Galpao;
import ps.smartwarehouse.inventario.domain.Produto;

import java.util.Comparator;
import java.util.Optional;


public class DensidadeMaxima implements AlocarStrategy {

    @Override
    public String nome() { return "DENSIDADE_MAXIMA"; }

    @Override
    public Optional<SlotCandidato> alocar(Produto produto, Galpao galpao) {
        return candidatos(galpao).stream()
                .min(Comparator.comparingInt(c -> c.slot().espacoLivre()));
    }
}
