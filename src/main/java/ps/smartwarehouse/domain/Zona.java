package ps.smartwarehouse.domain;
import java.util.ArrayList;
import java.util.Collection;
import ps.smartwarehouse.domain.sensor.Sensor;
import ps.smartwarehouse.domain.alocador.Alocador;

public class Zona {
    private String identificador;
    private boolean estaBloqueada;
    private Collection<Corredor> corredores;
    private Collection<Sensor> sensores; // sensores da zona
    private Alocador alocador; // estratégia de alocação

    public Zona(String identificador) {
        this.identificador = identificador;
        this.estaBloqueada = false;
        this.corredores = new ArrayList<>();
        this.sensores = new ArrayList<>();
        this.alocador = new Alocador(); // pode ser configurado depois
    }

    public void alocar(Object lote, Object slots) {
        if (lote instanceof Collection<?> && slots instanceof Collection<?>) {
            Collection<Produto> produtos = (Collection<Produto>) lote;
            alocador.alocar(produtos);
        } else {
            throw new IllegalArgumentException("Parâmetros inválidos para alocação");
        }
    }

    public void bloquear() {
        this.estaBloqueada = true;
    }

    public void desbloquear() {
        this.estaBloqueada = false;
    }


    public String getIdentificador() {
        return identificador;
    }
    public boolean isEstaBloqueada() {
        return estaBloqueada;
    }
    public Collection<Corredor> getCorredores() {
        return corredores;
    }
    public Collection<Sensor> getSensores() {
        return sensores;
    }
    public Alocador getAlocador() {
        return alocador;
    }
    public void setAlocador(Alocador alocador) {
        this.alocador = alocador;
    }
}
