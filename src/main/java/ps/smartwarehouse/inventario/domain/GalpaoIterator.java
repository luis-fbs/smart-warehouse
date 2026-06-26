package ps.smartwarehouse.inventario.domain;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class GalpaoIterator implements Iterador<Produto> {

    private final Iterator<Zona> zonas;
    private Iterator<Corredor> corredores = Collections.emptyIterator();
    private Iterator<Prateleira> prateleiras = Collections.emptyIterator();
    private Iterator<Slot> slots = Collections.emptyIterator();
    private Iterator<Produto> produtos = Collections.emptyIterator();

    public GalpaoIterator(Galpao galpao) {
        this.zonas = galpao.getZonas().iterator();
    }

    @Override
    public boolean temProximo() {
        while (!produtos.hasNext()) {
            if (slots.hasNext()) {
                produtos = slots.next().getProdutos().iterator();
            } else if (prateleiras.hasNext()) {
                slots = prateleiras.next().getSlots().iterator();
            } else if (corredores.hasNext()) {
                prateleiras = corredores.next().getPrateleiras().iterator();
            } else if (zonas.hasNext()) {
                corredores = zonas.next().getCorredores().iterator();
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public Produto proximo() {
        if (!temProximo()) throw new NoSuchElementException();
        return produtos.next();
    }
}
