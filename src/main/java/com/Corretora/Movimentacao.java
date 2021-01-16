package com.Corretora;

import java.util.LinkedList;
import java.math.BigDecimal;
import java.util.Hashtable;

// supondo que havera mais adicao que outras operacoes em compras e vendas,
// linkedList eh O(1) para inserir e o ArrayList O(n).
public class Movimentacao implements IMovimentacao {

    private static final int _2CASASDECIMAIS = 2;
    protected LinkedList<IMovimento> compras = new LinkedList<IMovimento>();
    protected LinkedList<IMovimento> vendas = new LinkedList<IMovimento>();
    protected Hashtable<String, Double> htQuantidadeAtivos = new Hashtable<String, Double>();

    public LinkedList<IMovimento> GetCompras() {
        try {
            return TentarGetMovimento(compras);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public LinkedList<String> GetAtivosEmPosse() {
        try {
            return TentarGetAtivosEmPosse();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public LinkedList<IMovimento> GetVendas() {
        try {
            return TentarGetMovimento(vendas);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
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
        try {
            TentarComprarAtivos(ativo, preco, quantidade, conta);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void VenderAtivos(IAtivo ativo, Double preco, Double quantidade, IContaCorrente conta) {
        try {
            TentarVenderAtivos(ativo, preco, quantidade, conta);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public LinkedList<IMovimento> TentarGetMovimento(LinkedList<IMovimento> movimentos) throws NullPointerException {
        if (movimentos.isEmpty()) {
            throw new NullPointerException();
        } else {
            return movimentos;
        }
    }

    public LinkedList<String> TentarGetAtivosEmPosse() throws NullPointerException {
        LinkedList<String> ativosEmPosse = new LinkedList<String>(htQuantidadeAtivos.keySet());
        if (ativosEmPosse.isEmpty()) {
            throw new NullPointerException();
        } else {
            return ativosEmPosse;
        }
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
        if (ValidarTransacao(ativo, preco, quantidade) != null){
            throw ValidarTransacao(ativo, preco, quantidade);
        } else {
            String mensagem = "Compra de Ativos: " + ativo.GetNome() + " | quantidade: " + quantidade;
            IMovimento movimento = new Movimento(ativo.GetNome(), preco, quantidade);
            conta.GetSaldo().LancarSaida(MultiplicarValorQuantidade(preco, quantidade), mensagem);
            AtualizarHTQuantidadeAtivos(ativo, quantidade);
            compras.add(movimento);
        }
    }

    public IllegalArgumentException ValidarTransacao(IAtivo ativo, Double preco, Double quantidade) {
        
        if (!ValidarValorPositivo(quantidade)) {
            return LancarValorNegativoExcecao("quantidade");
        } else if (!ValidarValorPositivo(preco)) {
            return LancarValorNegativoExcecao("preco");
        } else if (!ValidarCasasDecimais(quantidade)) {
            return LancarCasasDecimaisExcecao();
        } else {
            return null;
        }
    }

    public boolean ValidarCasasDecimais(Double valor) {
        boolean maiorQueDuasCasasDecimais = BigDecimal.valueOf(valor).scale() > _2CASASDECIMAIS;
        if (maiorQueDuasCasasDecimais) {
            return false;
        } else {
            return true;
        }
    }

    public void TentarVenderAtivos (IAtivo ativo, Double preco, Double quantidade, IContaCorrente conta) throws IllegalArgumentException {
        if (!ValidarExisteAtivo(ativo)) {
            throw LancarAtivoNuloExcecao();
        } else if (ValidarTransacao(ativo, preco, quantidade) != null){
            throw ValidarTransacao(ativo, preco, quantidade);
        } else if(!ValidarVenda(ativo, quantidade)){
            throw LancarAtivosInsuficientesExcecao();
        } else if (!ValidarExisteAtivo(ativo)) {
            throw LancarAtivoNuloExcecao();
        } else {
            String mensagem = "Venda de Ativos: " + ativo.GetNome() + " | quantidade: " + quantidade;
            IMovimento movimento = new Movimento(ativo.GetNome(), preco, quantidade);
            conta.GetSaldo().LancarEntrada(MultiplicarValorQuantidade(preco, quantidade), mensagem);
            AtualizarHTQuantidadeAtivos(ativo, -quantidade);
            vendas.add(movimento);
        }
    }

    private boolean ValidarExisteAtivo(IAtivo ativo) {
        if (htQuantidadeAtivos.get(ativo.GetNome()) == null){
            return false;
        } else {
            return true;
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

    public boolean ValidarValorPositivo(Double preco) {
        if (preco <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean ValidarVenda(IAtivo ativo, Double quantidade) {
        BigDecimal ativos = new BigDecimal(String.valueOf(GetQuantidadeAtivos(ativo)));
        BigDecimal quantidadeDecimal = new BigDecimal(String.valueOf(quantidade));
        if (ativos.subtract(quantidadeDecimal).compareTo(BigDecimal.ZERO) >= 0) {
            return true;
        } else {
            return false;
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
    

    public IllegalArgumentException LancarValorNegativoExcecao(String variavel) {
        IllegalArgumentException e = new IllegalArgumentException("Valor " + variavel + " precisa ser maior que zero");
        return e;
    }

    public IllegalArgumentException LancarAtivoNuloExcecao() {
        IllegalArgumentException e = new IllegalArgumentException("Nao foi possivel achar o ativo");
        return e;
    }

    public IllegalArgumentException LancarCasasDecimaisExcecao() {
        IllegalArgumentException e = new IllegalArgumentException("Numero quantidade com mais que 2 casas decimais");
        return e;
    }
}
