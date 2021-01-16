package com.Corretora;

import org.junit.Test;
import static org.junit.Assume.assumeNoException;

/**
 * Unit test for simple App.
 */
public class GerenciarContaTeste 
{
    @Test
    public void CasoDeUsoTeste() {
        try {
            IContaCorrente conta = new ContaCorrente();
            GerenciarContaCorrente gerenciar = new GerenciarContaCorrente();
            IControleAtivos controle = new ControleAtivos();
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Criar("RBRF11", 23.14d, Tipo.RV);
            controle.Criar("SEER3", 23.14d, Tipo.RV);
            gerenciar.LancarEntrada(10000d, "", conta);
            System.out.println(" Saldo inicial "+ gerenciar.ConsultarSaldo(conta));
            gerenciar.ComprarAtivos(controle.GetAtivo("SEER3"), 23.14d, 10d, conta);
            gerenciar.ComprarAtivos(controle.GetAtivo("YDUQ3"), 23.14d, 10d, conta);
            gerenciar.ComprarAtivos(controle.GetAtivo("RBRF11"), 13.14d, 10d, conta);
            gerenciar.ComprarAtivos(controle.GetAtivo("RBRF11"), 34.19d, 15d, conta);
            gerenciar.ComprarAtivos(controle.GetAtivo("RBRF11"), 44.58d, 25d, conta);
            gerenciar.VenderAtivos(controle.GetAtivo("RBRF11"), 84.68d, 25d, conta);
            controle.GetAtivo("RBRF11").SetValor(55.38d);
    
            gerenciar.ConsultarPosicao( conta, controle);
            System.out.println( "-------------" );
            System.out.println(" Saldo Final "+ gerenciar.ConsultarSaldo(conta));     
        } catch (Exception e) {
            assumeNoException(e);
        }

    }
}