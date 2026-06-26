package ps.smartwarehouse.inventario.domain;

import java.util.ArrayList;
import java.util.List;


public class Corredor {

    private final String identificador;
    private final List<Prateleira> prateleiras = new ArrayList<>();

    public Corredor(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() { return identificador; }
    public List<Prateleira> getPrateleiras() { return prateleiras; }

    public Corredor adicionarPrateleira(Prateleira prateleira) {
        prateleiras.add(prateleira);
        return this;
    }
}
