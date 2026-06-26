package ps.smartwarehouse.inventario.sensor;

import ps.smartwarehouse.inventario.domain.Zona;

import java.util.ArrayList;
import java.util.List;


public abstract class Sensor implements Subject {

    public enum Estado { NORMAL, ALERTA }

    private final List<Observer> observers = new ArrayList<>();
    private final String tipo;
    private final int limiteMin;
    private final int limiteMax;
    private final int classeRisco;
    private Zona zona;
    private Estado estado = Estado.NORMAL;
    private double leituraAtual;

    protected Sensor(String tipo, int limiteMin, int limiteMax, int classeRisco) {
        this.tipo = tipo;
        this.limiteMin = limiteMin;
        this.limiteMax = limiteMax;
        this.classeRisco = classeRisco;
    }

    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) o.update(this);
    }

    public void leitura(double valor) {
        this.leituraAtual = valor;
        boolean foraDosLimites = valor < limiteMin || valor > limiteMax;
        Estado novo = foraDosLimites ? Estado.ALERTA : Estado.NORMAL;
        boolean mudou = novo != this.estado;
        this.estado = novo;
        if (novo == Estado.ALERTA && mudou) {
            notifyObservers();
        }
    }

    public String getTipo() { return tipo; }
    public int getLimiteMin() { return limiteMin; }
    public int getLimiteMax() { return limiteMax; }
    public int getClasseRisco() { return classeRisco; }
    public Estado getEstado() { return estado; }
    public double getLeituraAtual() { return leituraAtual; }
    public Zona getZona() { return zona; }
    public void setZona(Zona zona) { this.zona = zona; }
}
