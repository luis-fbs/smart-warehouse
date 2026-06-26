package ps.smartwarehouse.domain;
import java.util.ArrayList;
import java.util.Collection;

public class Corredor {
    private String identificador;
    private Collection<Prateleira> prateleiras;

    public Corredor(String identificador) {
        this.identificador = identificador;
        this.prateleiras = new ArrayList<>();
    }

    public String getIdentificador() {
        return identificador;
    }
    public Collection<Prateleira> getPrateleiras() {
        return prateleiras;
    }
}
