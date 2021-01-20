package com.corretora.model;

import com.corretora.enums.Tipo;
import com.corretora.interfaces.IAtivo;

public class Ativo implements IAtivo {
    private String nome;
    private double valor;
    private Tipo tipo;

    public String GetNome() {
        return nome;
    }

    public void SetNome(String nome) {
        this.nome = nome;
    }

    public double GetValor() {
        return valor;
    }

    public void SetValor(double valor) {
        this.valor = valor;
    }

    public Tipo GetTipo() {
        return tipo;
    }

    public void SetTipo(Tipo tipo) {
        this.tipo = tipo;
    }

}
