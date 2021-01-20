package com.corretora.interfaces;

public interface ISaldo {
    public void LancarEntrada(Double valor, String string);
    public void LancarSaida(Double valor, String string);
    public Double Consultar();
}
