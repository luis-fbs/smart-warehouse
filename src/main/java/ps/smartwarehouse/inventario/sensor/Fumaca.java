package ps.smartwarehouse.inventario.sensor;


public class Fumaca extends Sensor {
    public Fumaca(int limiteMax) {
        super("FUMACA", 0, limiteMax, 3);
    }
}
