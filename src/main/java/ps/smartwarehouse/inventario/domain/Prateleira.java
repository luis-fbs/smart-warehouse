package ps.smartwarehouse.inventario.domain;

import java.util.ArrayList;
import java.util.List;


public class Prateleira {

    private final String identificador;
    private final List<Slot> slots = new ArrayList<>();
    private final int capacidade;

    public Prateleira(String identificador, int capacidade) {
        this.identificador = identificador;
        this.capacidade = capacidade;
    }

    public String getIdentificador() { return identificador; }
    public List<Slot> getSlots() { return slots; }
    public int getCapacidade() { return capacidade; }

    public boolean estaCheia() {
        return slots.stream().allMatch(Slot::estaCheio);
    }

    public Prateleira adicionarSlot(Slot slot) {
        slots.add(slot);
        return this;
    }
}
