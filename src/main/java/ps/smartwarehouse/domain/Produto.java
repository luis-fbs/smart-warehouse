package ps.smartwarehouse.domain;
import java.time.LocalDate;

public class Produto {

    private final String    nome;
    private final LocalDate validade;
    private final String    categoria;
    private final int       risco;        // 1 (baixo) a 5 (alto)
    private final float     indiceSaida;  // 0.0 (parado) a 1.0 (giro diário)

    public Produto(String nome, LocalDate validade,
                   String categoria, int risco, float indiceSaida) {
        this.nome        = nome;
        this.validade    = validade;
        this.categoria   = categoria;
        this.risco       = risco;
        this.indiceSaida = indiceSaida;
    }

    public String    getNome()       { return nome; }
    public LocalDate getValidade()   { return validade; }
    public String    getCategoria()  { return categoria; }
    public int       getRisco()      { return risco; }
    public float     getIndiceSaida(){ return indiceSaida; }

    @Override
    public String toString() {
        return String.format("Produto{nome='%-22s validade=%s, categoria=%-10s risco=%d, indiceSaida=%.2f}",
                nome + "'", validade, "'" + categoria + "',", risco, indiceSaida);
    }
}