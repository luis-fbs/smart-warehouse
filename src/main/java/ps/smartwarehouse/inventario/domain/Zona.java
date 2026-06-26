package ps.smartwarehouse.inventario.domain;

import ps.smartwarehouse.inventario.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;


public class Zona {

    private final String identificador;
    private boolean estaBloqueada = false;
    private final List<Corredor> corredores = new ArrayList<>();
    private final List<Sensor> sensores = new ArrayList<>();
    private final int distanciaDoca;

    public Zona(String identificador, int distanciaDoca) {
        this.identificador = identificador;
        this.distanciaDoca = distanciaDoca;
    }

    public String getIdentificador() { return identificador; }
    public boolean zonaEstaBloqueada() { return estaBloqueada; }
    public List<Corredor> getCorredores() { return corredores; }
    public List<Sensor> getSensores() { return sensores; }
    public int getDistanciaDoca() { return distanciaDoca; }

    public void bloquear() { this.estaBloqueada = true; }

    public void desbloquear() { this.estaBloqueada = false; }

    public Zona adicionarCorredor(Corredor corredor) {
        corredores.add(corredor);
        return this;
    }

    public Zona adicionarSensor(Sensor sensor) {
        sensores.add(sensor);
        return this;
    }

    public boolean suportaRisco(Produto produto) {
        if (!produto.exigeControleAmbiental()) return true;
        return sensores.stream().anyMatch(s -> s.getClasseRisco() >= produto.getRisco());
    }
}
