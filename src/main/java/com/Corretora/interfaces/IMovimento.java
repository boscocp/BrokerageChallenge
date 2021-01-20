package com.corretora.interfaces;

import java.util.Calendar;

public interface IMovimento {
    public Calendar GetData();
    public Double GetPreco();
    public Double GetQuantidade();
    public String GetNome();
}
