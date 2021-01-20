package com.corretora;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.LinkedList;

import com.corretora.exception.ValorNegativoException;
import com.corretora.interfaces.IAtivo;
import com.corretora.interfaces.IContaCorrente;
import com.corretora.interfaces.IMovimentacao;
import com.corretora.interfaces.IMovimento;
import com.corretora.model.Movimento;

// supondo que havera mais adicao que outras operacoes em compras e vendas,
// linkedList eh O(1) para inserir e o ArrayList O(n).
public class Movimentacao implements IMovimentacao {

    private static final int _2CASASDECIMAIS = 2;
    protected LinkedList<IMovimento> compras = new LinkedList<IMovimento>();
    protected LinkedList<IMovimento> vendas = new LinkedList<IMovimento>();
    protected Hashtable<String, Double> htQuantidadeAtivos = new Hashtable<String, Double>();

    public LinkedList<IMovimento> GetCompras() {
        if (compras == null) {
            compras = new LinkedList<IMovimento>();
        }
        return compras;
    }

    public LinkedList<String> GetAtivosEmPosse() {
        if (htQuantidadeAtivos == null)
            htQuantidadeAtivos = new Hashtable<String, Double>();
        return TentarGetAtivosEmPosse();
    }

    public LinkedList<IMovimento> GetVendas() {
        if (vendas == null) {
            vendas = new LinkedList<IMovimento>();
        }
        return vendas;
    }

    public Double GetQuantidadeAtivos(IAtivo ativo) {
        try {
            return TentarQuantidadeAtivosEmPosse(ativo);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void ComprarAtivos(IAtivo ativo, Double preco, Double quantidade, IContaCorrente conta) {
        TentarComprarAtivos(ativo, preco, quantidade, conta);
    }

    public void VenderAtivos(IAtivo ativo, Double preco, Double quantidade, IContaCorrente conta) {
        try {
            TentarVenderAtivos(ativo, preco, quantidade, conta);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    private LinkedList<String> TentarGetAtivosEmPosse() {
        LinkedList<String> ativosEmPosse = new LinkedList<String>(htQuantidadeAtivos.keySet());
        return ativosEmPosse;
    }

    public Double TentarQuantidadeAtivosEmPosse(IAtivo ativo) throws NullPointerException {
        Double ativos = htQuantidadeAtivos.get(ativo.GetNome());
        if (ativos == null) {
            throw new NullPointerException();
        } else {
            return ativos;
        }
    }

    public void TentarComprarAtivos(IAtivo ativo, Double preco, Double quantidade, IContaCorrente conta)
            throws IllegalArgumentException {
        ValidarTransacao(ativo, preco, quantidade);
        String mensagem = "Compra de Ativos: " + ativo.GetNome() + " | quantidade: " + quantidade;
        IMovimento movimento = new Movimento(ativo.GetNome(), preco, quantidade);
        conta.GetSaldo().LancarSaida(MultiplicarValorQuantidade(preco, quantidade), mensagem);
        AtualizarHTQuantidadeAtivos(ativo, quantidade);
        compras.add(movimento);

    }

    public void ValidarTransacao(IAtivo ativo, Double preco, Double quantidade) throws IllegalArgumentException {
        try {
            ValidarValorPositivo(quantidade, "quantidade");
            ValidarValorPositivo(preco, "preco");
            ValidarCasasDecimais(quantidade);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public void ValidarCasasDecimais(Double valor) throws IllegalArgumentException {
        boolean maiorQueDuasCasasDecimais = BigDecimal.valueOf(valor).scale() > _2CASASDECIMAIS;
        if (maiorQueDuasCasasDecimais) {
            throw LancarCasasDecimaisExcecao();
        }
    }

    public void TentarVenderAtivos(IAtivo ativo, Double preco, Double quantidade, IContaCorrente conta)
            throws IllegalArgumentException {

        ValidarExisteAtivo(ativo);
        ValidarTransacao(ativo, preco, quantidade);
        ValidarVenda(ativo, quantidade);
        String mensagem = "Venda de Ativos: " + ativo.GetNome() + " | quantidade: " + quantidade;
        IMovimento movimento = new Movimento(ativo.GetNome(), preco, quantidade);
        conta.GetSaldo().LancarEntrada(MultiplicarValorQuantidade(preco, quantidade), mensagem);
        AtualizarHTQuantidadeAtivos(ativo, -quantidade);
        vendas.add(movimento);
    }

    private void ValidarExisteAtivo(IAtivo ativo) throws IllegalArgumentException {
        if (htQuantidadeAtivos.get(ativo.GetNome()) == null) {
            throw LancarAtivoNuloExcecao();
        }
    }

    public Double MultiplicarValorQuantidade(Double valor, Double quantidade) {
        BigDecimal quantidadeDecimal = new BigDecimal(String.valueOf(quantidade));
        BigDecimal valorDecimal = new BigDecimal(String.valueOf(valor));
        BigDecimal valorTotal = quantidadeDecimal.multiply(valorDecimal);
        return valorTotal.doubleValue();
    }

    public void AtualizarHTQuantidadeAtivos(IAtivo ativo, Double quantidade) {
        if (htQuantidadeAtivos.containsKey(ativo.GetNome())) {
            BigDecimal quantidadeAtualDecimal = new BigDecimal(String.valueOf(GetQuantidadeAtivos(ativo)));
            BigDecimal quantidadeCompradaDecimal = new BigDecimal(String.valueOf(quantidade));
            BigDecimal novaQuantidadeDecimal;
            novaQuantidadeDecimal = quantidadeCompradaDecimal.add(quantidadeAtualDecimal);
            htQuantidadeAtivos.put(ativo.GetNome(), novaQuantidadeDecimal.doubleValue());
        } else {
            htQuantidadeAtivos.put(ativo.GetNome(), quantidade);
        }
    }

    public void ValidarValorPositivo(Double valor, String campo) {
        if (valor <= 0) {
            throw new ValorNegativoException(campo);
        }
    }

    public void ValidarVenda(IAtivo ativo, Double quantidade) throws IllegalArgumentException {
        BigDecimal ativos = new BigDecimal(String.valueOf(GetQuantidadeAtivos(ativo)));
        BigDecimal quantidadeDecimal = new BigDecimal(String.valueOf(quantidade));
        if (ativos.subtract(quantidadeDecimal).compareTo(BigDecimal.ZERO) < 0) {
            throw LancarAtivosInsuficientesExcecao();
        }
    }

    public boolean ValidarQuantidadeVenda(IAtivo ativo, Double quantidade) {
        if (GetQuantidadeAtivos(ativo) - quantidade < 0d) {
            return false;
        } else {
            return true;
        }
    }

    public IllegalArgumentException LancarAtivosInsuficientesExcecao() {
        IllegalArgumentException e = new IllegalArgumentException("Ativos insuficientes");
        return e;
    }

    // public IllegalArgumentException LancarValorNegativoExcecao(String variavel) {
    //     IllegalArgumentException e = new IllegalArgumentException("Valor " + variavel + " precisa ser maior que zero");
    //     return e;
    // }

    public IllegalArgumentException LancarAtivoNuloExcecao() {
        IllegalArgumentException e = new IllegalArgumentException("Nao foi possivel achar o ativo");
        return e;
    }

    public IllegalArgumentException LancarCasasDecimaisExcecao() {
        IllegalArgumentException e = new IllegalArgumentException("Numero quantidade com mais que 2 casas decimais");
        return e;
    }

    public void Baco() throws ArithmeticException {
        throw new NumberFormatException("/ by zero");
    }
}
