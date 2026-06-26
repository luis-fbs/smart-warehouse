package ps.smartwarehouse.inventario.sensor;


public class Umidade extends Sensor {
    public Umidade(int limiteMin, int limiteMax) {
        super("UMIDADE", limiteMin, limiteMax, 1);
    }
}
