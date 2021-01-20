package com.corretora.interfaces;

import com.corretora.enums.Tipo;

public interface IAtivo {
    public String GetNome();
    public double GetValor();
    public Tipo GetTipo();
}
