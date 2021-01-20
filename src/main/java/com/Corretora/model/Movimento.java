package com.corretora.model;
import java.util.Calendar;

import com.corretora.interfaces.IMovimento;

public class Movimento implements IMovimento{
    protected Calendar data;
    protected Double preco;
    protected Double quantidade;
    protected String nome;

    public Movimento(String nome, Double preco, Double quantidade) {
        data = Calendar.getInstance();
        this.preco = preco;
        this.quantidade = quantidade;
        this.nome = nome;
    }

    public Calendar GetData() {
        return data;
    }

    public Double GetPreco() {
        return preco;
    }

    public Double GetQuantidade() {
        return quantidade;
    }

    public String GetNome() {
        return nome;
    }
}
