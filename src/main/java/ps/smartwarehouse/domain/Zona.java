package ps.smartwarehouse.domain;

import java.util.ArrayList;
import java.util.List;

import ps.smartwarehouse.domain.alocador.Alocador;
import ps.smartwarehouse.domain.sensor.Sensor;

public class Zona {
    private String identificador;
    private boolean estaBloqueada;
    private List<Corredor> corredores;
    private List<Sensor> sensores;
    private Alocador alocador;

    public Zona(String identificador) {
        this.identificador = identificador;
        this.estaBloqueada = false;
        this.corredores = new ArrayList<>();
        this.sensores = new ArrayList<>();
        this.alocador = new Alocador();
    }

    public void alocar(List<Produto> produtos) {
        alocador.alocar(produtos, null);
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
    public List<Corredor> getCorredores() {
        return corredores;
    }
    public List<Sensor> getSensores() {
        return sensores;
    }
    public Alocador getAlocador() {
        return alocador;
    }
    public void setAlocador(Alocador alocador) {
        this.alocador = alocador;
    }
}