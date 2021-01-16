package com.Corretora;

import java.util.Calendar;

interface IMovimento {
    public Calendar GetData();
    public Double GetPreco();
    public Double GetQuantidade();
    public String GetNome();
}
