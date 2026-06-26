package ps.smartwarehouse.inventario.domain;

import java.time.LocalDate;


public class Produto {

    public enum Categoria { COMUM, PERECIVEL, QUIMICO }

    private final String nome;
    private final LocalDate validade;
    private final Categoria categoria;
    private final int risco;
    private final double indiceSaida;

    public Produto(String nome, LocalDate validade, Categoria categoria, int risco, double indiceSaida) {
        this.nome = nome;
        this.validade = validade;
        this.categoria = categoria;
        this.risco = risco;
        this.indiceSaida = indiceSaida;
    }

    public String getNome() { return nome; }
    public LocalDate getValidade() { return validade; }
    public Categoria getCategoria() { return categoria; }
    public int getRisco() { return risco; }
    public double getIndiceSaida() { return indiceSaida; }

    public boolean exigeControleAmbiental() {
        return categoria == Categoria.PERECIVEL || categoria == Categoria.QUIMICO;
    }

    @Override
    public String toString() {
        return nome;
    }
}
