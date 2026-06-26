package ps.smartwarehouse.domain.alocador;

import ps.smartwarehouse.domain.Galpao;
import ps.smartwarehouse.domain.Produto;
import ps.smartwarehouse.domain.Slot;

import java.util.List;
import java.util.Map;

public class Alocador {

    private AlocadorStrategy estrategia;

    public Alocador() {
        this.estrategia = null;
        System.out.println("[ALOCADOR] Iniciado sem estrategia");
    }

    public Alocador(AlocadorStrategy estrategia) {
        this.estrategia = estrategia;
        System.out.printf("[ALOCADOR] Iniciado com estrategia: %s%n",
                estrategia.getClass().getSimpleName());
    }

    /** Troca a estratégia em tempo de execução — sem alterar o Alocador. */
    public void setEstrategia(AlocadorStrategy novaEstrategia) {
        System.out.printf("%n[ALOCADOR] Estrategia trocada: %s -> %s%n",
                estrategia.getClass().getSimpleName(),
                novaEstrategia.getClass().getSimpleName());
        this.estrategia = novaEstrategia;
    }

    public Map<Produto, Slot> alocar(List<Produto> lote, Galpao galpao) {
        System.out.printf("[ALOCADOR] Processando lote com %d produto(s) via %s...%n",
                lote.size(), estrategia.getClass().getSimpleName());
        return estrategia.alocar(lote, galpao);
    }
}