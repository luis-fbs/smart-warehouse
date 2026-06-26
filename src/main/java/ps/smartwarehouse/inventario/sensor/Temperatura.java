package ps.smartwarehouse.inventario.sensor;


public class Temperatura extends Sensor {
    public Temperatura(int limiteMin, int limiteMax) {
        super("TEMPERATURA", limiteMin, limiteMax, 2);
    }
}
