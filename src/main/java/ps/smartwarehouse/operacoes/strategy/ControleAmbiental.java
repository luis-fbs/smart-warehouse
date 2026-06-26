package ps.smartwarehouse.operacoes.strategy;

import ps.smartwarehouse.inventario.domain.Galpao;
import ps.smartwarehouse.inventario.domain.Produto;

import java.util.Comparator;
import java.util.Optional;


public class ControleAmbiental implements AlocarStrategy {

    @Override
    public String nome() { return "CONTROLE_AMBIENTAL"; }

    @Override
    public Optional<SlotCandidato> alocar(Produto produto, Galpao galpao) {
        return candidatos(galpao).stream()
                .filter(c -> c.zona().suportaRisco(produto))
                .min(Comparator.comparingInt(c -> c.slot().getNivel()));
    }
}
