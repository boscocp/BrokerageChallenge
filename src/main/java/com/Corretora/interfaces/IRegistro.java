package com.corretora.interfaces;

import com.corretora.enums.Tipo;

public interface IRegistro {
    public String GetNome ();
    public Tipo GetTipo ();
    public Double GetQuantidade ();
    public Double GetValorMercadoTotal ();
    public Double GetRendimento ();
    public Double GetLucro();
}
