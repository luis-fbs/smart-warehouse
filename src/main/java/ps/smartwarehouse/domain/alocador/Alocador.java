package ps.smartwarehouse.domain.alocador;
import ps.smartwarehouse.domain.Produto;
import java.util.Collection;

public class Alocador {
    private AlocadorStrategy estrategia;

    public Alocador() {
        this.estrategia = new GiroRapido();
    }

    public void setEstrategia(AlocadorStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void alocar(Collection<Produto> produtos) {
        estrategia.alocar(produtos);
    }
}