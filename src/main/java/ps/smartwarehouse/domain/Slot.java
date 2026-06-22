package ps.smartwarehouse.domain;
import java.util.ArrayList;
import java.util.Collection;


public class Slot {
    private String identificador;
    private Collection<Produto> produtos;
    private int capacidade;

    public Slot(String identificador, int capacidade) {
        this.identificador = identificador;
        this.capacidade = capacidade;
        this.produtos = new ArrayList<>();
    }

    public boolean estaCheio() {
        return produtos.size() < capacidade;
    }

    public void adicionarProduto(Produto p) {
        if (estaCheio()) {
            produtos.add(p);
        } else {
            throw new IllegalStateException("Slot cheio");
        }
    }

    // Getters e Setters
    public String getIdentificador() { return identificador; }
    public Collection<Produto> getProdutos() { return produtos; }
    public int getCapacidade() { return capacidade; }
}
