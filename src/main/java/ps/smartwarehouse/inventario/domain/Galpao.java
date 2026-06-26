package ps.smartwarehouse.inventario.domain;

import java.util.ArrayList;
import java.util.List;


public class Galpao {

    private final String identificador;
    private final List<Zona> zonas = new ArrayList<>();

    public Galpao(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() { return identificador; }
    public List<Zona> getZonas() { return zonas; }

    public Galpao adicionarZona(Zona zona) {
        zonas.add(zona);
        return this;
    }

    public Iterador<Produto> iteradorDeProdutos() {
        return new GalpaoIterator(this);
    }
}
