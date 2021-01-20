package com.corretora.interfaces;

import java.util.LinkedList;

public interface IMovimentacao {
    public void ComprarAtivos(IAtivo ativo, Double preco, Double quantidade, IContaCorrente conta);

    public void VenderAtivos(IAtivo ativo, Double preco, Double quantidade, IContaCorrente conta);
    
    public LinkedList<IMovimento> GetCompras(); 

    public LinkedList<IMovimento> GetVendas();

    public Double GetQuantidadeAtivos(IAtivo ativo);

    public LinkedList<String> GetAtivosEmPosse();
}
