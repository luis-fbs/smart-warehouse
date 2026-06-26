package ps.smartwarehouse.domain.sensor;

public abstract class Sensor {
    private String tipo;

    public Sensor(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
