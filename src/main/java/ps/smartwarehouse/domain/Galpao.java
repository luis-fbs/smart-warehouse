package ps.smartwarehouse.domain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class Galpao {
    private String identificador;
    private Collection<Zona> zonas;
    public Galpao(String identificador) {
        this.identificador = identificador;
        this.zonas = new ArrayList<>();
    }
    public void alocar(Object lote, Object slots) {
        for (Zona zona : zonas) {
            zona.alocar(lote, slots);
        }
    }
    public void bloquear() {
        for (Zona zona : zonas) {
            zona.bloquear();
        }
    }
    public void desbloquear() {
        for (Zona zona : zonas) {
            zona.desbloquear();
        }
    }
    public String getIdentificador() {
        return identificador;
    }
    public Collection<Zona> getZonas() {
        return zonas;
    }
}
