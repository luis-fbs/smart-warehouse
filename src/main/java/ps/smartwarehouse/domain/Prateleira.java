package ps.smartwarehouse.domain;
import java.util.ArrayList;
import java.util.Collection;

public class Prateleira {
    private String identificador;
    private Collection<Slot> slots;
    private int capacidade; // capacidade total? No diagrama está em Prateleira, mas não é usado.
    private boolean estaCheia; // pode ser calculado

    public Prateleira(String identificador, int capacidade) {
        this.identificador = identificador;
        this.capacidade = capacidade;
        this.slots = new ArrayList<>();
        this.estaCheia = false;
    }

    public boolean estaCheia() {
        // Verifica se todos os slots estão cheios
        for (Slot s : slots) {
            if (s.estaCheio()) return false;
        }
        return true;
    }


    public String getIdentificador() { return identificador; }
    public Collection<Slot> getSlots() { return slots; }
    public int getCapacidade() { return capacidade; }
    public void setEstaCheia(boolean estaCheia) { this.estaCheia = estaCheia; }
}
