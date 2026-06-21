package ps.smartwarehouse.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Produtos implements Iterable<Produto> {
    public List<Produto> produtos;

    public  Produtos() {
        this.produtos = new ArrayList<Produto>();
    }

    @Override
    public Iterator iterator() {
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
