package ps.smartwarehouse.operacoes.strategy;

import ps.smartwarehouse.inventario.domain.Galpao;
import ps.smartwarehouse.inventario.domain.Produto;

import java.util.Optional;


public class Alocador {

    private AlocarStrategy estrategia;

    public Alocador(AlocarStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void setEstrategia(AlocarStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public AlocarStrategy getEstrategia() {
        return estrategia;
    }

    public Optional<SlotCandidato> alocar(Produto produto, Galpao galpao) {
        return estrategia.alocar(produto, galpao);
    }
}
