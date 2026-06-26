package ps.smartwarehouse.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Produtos implements Iterable<Produto> {
    private List<Produto> produtos;

    public Produtos() {
        this.produtos = new ArrayList<>();
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void adicionar(Produto produto) {
        produtos.add(produto);
    }

    @Override
    public Iterator<Produto> iterator() {
        return new Iterator<Produto>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < produtos.size();
            }

            @Override
            public Produto next() {
                return produtos.get(index++);
            }
        };
    }
}
