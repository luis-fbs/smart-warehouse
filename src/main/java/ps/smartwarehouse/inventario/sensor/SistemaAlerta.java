package ps.smartwarehouse.inventario.sensor;

import ps.smartwarehouse.comum.EventoLog;


public class SistemaAlerta implements Observer {

    private final EventoLog log;

    public SistemaAlerta(EventoLog log) {
        this.log = log;
    }

    @Override
    public void update(Sensor sensor) {
        String zona = sensor.getZona() != null ? sensor.getZona().getIdentificador() : "?";
        log.registrar(String.format(
                "SENSOR [%s] Zona %s detectou leitura crítica %.1f (limites %d..%d) -> ALERTA",
                sensor.getTipo(), zona, sensor.getLeituraAtual(),
                sensor.getLimiteMin(), sensor.getLimiteMax()));
    }
}
