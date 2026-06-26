package ps.smartwarehouse.domain.alocador;

import ps.smartwarehouse.domain.Galpao;
import ps.smartwarehouse.domain.Produto;
import ps.smartwarehouse.domain.Slot;

import java.util.List;
import java.util.Map;

public interface AlocadorStrategy {
    Map<Produto, Slot> alocar(List<Produto> produtos, Galpao galpao);
}
