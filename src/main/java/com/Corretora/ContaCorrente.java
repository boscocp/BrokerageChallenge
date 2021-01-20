package com.corretora;

import com.corretora.interfaces.IConsulta;
import com.corretora.interfaces.IContaCorrente;
import com.corretora.interfaces.IMovimentacao;
import com.corretora.interfaces.ISaldo;

public class ContaCorrente implements IContaCorrente {
    protected ISaldo saldo;
    protected IMovimentacao movimentacao; 
    protected IConsulta consulta; 

    public ContaCorrente () {
        saldo = new Saldo(); 
        movimentacao = new Movimentacao();
        consulta = new Consulta();
    }

    public ISaldo GetSaldo() {
        return saldo;
    }

    public IMovimentacao GetMovimentacao() {
        return movimentacao;
    }

    public IConsulta GetConsulta() {
        return consulta;
    }
}