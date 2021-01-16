package com.Corretora;

import java.util.ArrayList;

public class GerenciarContaCorrente {

    public void ComprarAtivos(IAtivo ativo, Double preco, Double quantidade, IContaCorrente conta) {
        try {
            conta.GetMovimentacao().ComprarAtivos(ativo, preco, quantidade, conta);
        } catch (Exception e) {
            throw e;
        }
    }

    public void VenderAtivos(IAtivo ativo, Double preco, Double quantidade, IContaCorrente conta) {
        try {
            conta.GetMovimentacao().VenderAtivos(ativo, preco, quantidade, conta);
        } catch (Exception e) {
            throw e;
        }
    }

    public Double ConsultarSaldo(IContaCorrente conta) {
        return conta.GetSaldo().Consultar();
    }

    public void LancarEntrada(Double valor, String descricao, IContaCorrente conta) {
        try {
            conta.GetSaldo().LancarEntrada(valor, descricao);            
        } catch (Exception e) {
            throw e;
        }
    }

    public void LancarSaida(Double valor, String descricao, IContaCorrente conta) {
        try {
            conta.GetSaldo().LancarSaida(valor, descricao);
        } catch (Exception e) {
            throw e;
        }
    }

    public void ConsultarPosicao(IContaCorrente conta, IControleAtivos controleAtivos) {
        for (String ativoEmPosse : conta.GetMovimentacao().GetAtivosEmPosse()) {
            try {
                conta.GetConsulta().CriarRegistro(controleAtivos.GetAtivo(ativoEmPosse), conta.GetMovimentacao());                
            } catch (Exception e) {
                throw e;
            }
        }

        for (String ativoEmPosse : conta.GetMovimentacao().GetAtivosEmPosse()) {
            ArrayList<IRegistro> registros = conta.GetConsulta()
                    .GetRegistroPorAtivo(controleAtivos.GetAtivo(ativoEmPosse));
            for (IRegistro registro : registros) {
                System.out.println("--------------------------------------------------------------------------------");
                System.out.println(registro.GetNome() + " | Quantidade " + registro.GetQuantidade()
                        + " | Valor de mercado total " + registro.GetValorMercadoTotal() + " | Rendimento "
                        + registro.GetRendimento() + " | Lucro " + registro.GetLucro());
            }
        }
    }
}
