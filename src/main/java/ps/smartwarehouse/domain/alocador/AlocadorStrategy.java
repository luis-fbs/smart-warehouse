package ps.smartwarehouse.domain.alocador;
import ps.smartwarehouse.domain.Produto;

import java.util.Collection;

public interface AlocadorStrategy {
    void alocar(Collection<Produto> produtos);
}
