package ps.smartwarehouse.inventario.domain;


public interface Iterador<T> {
    boolean temProximo();
    T proximo();
}
