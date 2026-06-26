package ps.smartwarehouse.operacoes.strategy;

import ps.smartwarehouse.inventario.domain.Slot;
import ps.smartwarehouse.inventario.domain.Zona;


public record SlotCandidato(Zona zona, Slot slot) {
}
