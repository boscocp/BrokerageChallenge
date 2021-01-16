package com.Corretora;

public class Registro implements IRegistro {
    protected String nome;
    protected Tipo tipo;
    protected Double quantidade;
    protected Double valorDeMercadoTotal;
    protected Double rendimento;
    protected Double lucro;

    public Registro(String nome, Tipo tipo, Double quantidade, Double valorDeMercadoTotal, Double rendimento,
            Double lucro) {
        this.nome = nome;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.valorDeMercadoTotal = valorDeMercadoTotal;
        this.rendimento = rendimento;
        this.lucro = lucro;
    }

    public String GetNome() {
        return nome;
    }

    public Tipo GetTipo() {
        return tipo;
    }

    public Double GetQuantidade() {
        return quantidade;
    }

    public Double GetValorMercadoTotal() {
        return valorDeMercadoTotal;
    }

    public Double GetRendimento() {
        return rendimento;
    }

    public Double GetLucro() {
        return lucro;
    }
}
