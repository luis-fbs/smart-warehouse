package ps.smartwarehouse.operacoes.robo;


public class Robo {

    public enum Estado { OPERANDO, EVACUANDO }

    private final String id;
    private String zonaAtual;
    private Estado estado = Estado.OPERANDO;

    public Robo(String id, String zonaAtual) {
        this.id = id;
        this.zonaAtual = zonaAtual;
    }

    public String getId() { return id; }
    public String getZonaAtual() { return zonaAtual; }
    public void setZonaAtual(String zonaAtual) { this.zonaAtual = zonaAtual; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
}
