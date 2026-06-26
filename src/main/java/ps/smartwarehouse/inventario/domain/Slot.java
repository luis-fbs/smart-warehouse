package ps.smartwarehouse.inventario.domain;

import java.util.ArrayList;
import java.util.List;


public class Slot {

    private final String identificador;
    private final List<Produto> produtos = new ArrayList<>();
    private final int capacidade;
    private final int nivel;

    public Slot(String identificador, int capacidade, int nivel) {
        this.identificador = identificador;
        this.capacidade = capacidade;
        this.nivel = nivel;
    }

    public String getIdentificador() { return identificador; }
    public List<Produto> getProdutos() { return produtos; }
    public int getCapacidade() { return capacidade; }
    public int getNivel() { return nivel; }

    public boolean estaCheio() { return produtos.size() >= capacidade; }

    public int espacoLivre() { return capacidade - produtos.size(); }

    public boolean armazenar(Produto produto) {
        if (estaCheio()) return false;
        return produtos.add(produto);
    }
}
