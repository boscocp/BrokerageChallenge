package com.corretora;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import com.corretora.enums.Tipo;
import com.corretora.interfaces.IAtivo;
import com.corretora.interfaces.IConsulta;
import com.corretora.interfaces.IMovimentacao;
import com.corretora.interfaces.IMovimento;
import com.corretora.interfaces.IRegistro;
import com.corretora.model.Registro;

public class Consulta implements IConsulta {
    protected Hashtable<String, ArrayList<IRegistro>> registros = new Hashtable<String, ArrayList<IRegistro>>();

    public ArrayList<IRegistro> GetRegistroPorAtivo(IAtivo ativo) {
        return registros.get(ativo.GetNome());
    }

    public void CriarRegistro(IAtivo ativo, IMovimentacao movimentacao) {
        try {
            TentarCriarRegistro(ativo, movimentacao);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void TentarCriarRegistro(IAtivo ativo, IMovimentacao movimentacao) throws IllegalArgumentException {
        if (ativo.GetNome() == null && ativo.GetTipo() == null && ativo.GetValor() <= 0) {
            throw LancarCriarRegistroExcecao();
        } else {
            String nome = ativo.GetNome();
            Tipo tipo = ativo.GetTipo();
            Double quantidade = CalcularQuantidade(ativo, movimentacao);
            Double valorDeMercadoTotal = CalcularValorMercadoTotal(ativo, quantidade);
            Double rendimento = CalcularRendimento(ativo, movimentacao);
            Double lucro = CalcularLucro(ativo, movimentacao);
            IRegistro registro = new Registro(nome, tipo, quantidade, valorDeMercadoTotal, rendimento, lucro);

            if (registros.containsKey(nome)) {
                registros.get(nome).add(registro);
            } else {
                ArrayList<IRegistro> reg = new ArrayList<IRegistro>();
                reg.add(registro);
                registros.put(nome, reg);
            }
        }
    }

    public Double CalcularLucro(IAtivo ativo, IMovimentacao movimentacao) {
        BigDecimal somaCompra = BigDecimal.ZERO;
        BigDecimal somaVenda = BigDecimal.ZERO;
        BigDecimal resultado = BigDecimal.ZERO;
        LinkedList<IMovimento> compras;
        LinkedList<IMovimento> vendas;
        
        compras = TratarListaCompras(movimentacao);
        vendas = TratarListaVendas(movimentacao);

        if (vendas.isEmpty() && compras.isEmpty()) {
            return 0d;
        }

        somaCompra = SomarCompras(ativo, movimentacao, somaCompra);
        somaVenda = SomarVendas(ativo, movimentacao, somaVenda);
        resultado = somaVenda.subtract(somaCompra);
        return resultado.doubleValue();
    }

    private BigDecimal SomarVendas(IAtivo ativo, IMovimentacao movimentacao, BigDecimal somaVenda) {
        for (IMovimento movimento : movimentacao.GetVendas()) {
            if (movimento.GetNome().equals(ativo.GetNome())) {
                BigDecimal preco = BigDecimal.valueOf(movimento.GetPreco());
                BigDecimal quantidade = BigDecimal.valueOf(movimento.GetQuantidade());
                BigDecimal soma = preco.multiply(quantidade);
                somaVenda = somaVenda.add(soma);
            }
        }
        return somaVenda;
    }

    private BigDecimal SomarCompras(IAtivo ativo, IMovimentacao movimentacao, BigDecimal somaCompra) {
        for (IMovimento movimento : movimentacao.GetCompras()) {
            if (movimento.GetNome().equals(ativo.GetNome())) {
                BigDecimal preco = BigDecimal.valueOf(movimento.GetPreco());
                BigDecimal quantidade = BigDecimal.valueOf(movimento.GetQuantidade());
                BigDecimal soma = preco.multiply(quantidade);
                somaCompra = somaCompra.add(soma);
            }
        }
        return somaCompra;
    }

    private LinkedList<IMovimento> TratarListaVendas(IMovimentacao movimentacao) {
        LinkedList<IMovimento> vendas;
        try {
            vendas = movimentacao.GetVendas();
        } catch (Exception e) {
            vendas = new LinkedList<IMovimento>();
        }
        return vendas;
    }

    private LinkedList<IMovimento> TratarListaCompras(IMovimentacao movimentacao) {
        LinkedList<IMovimento> compras;
        try {
            compras = movimentacao.GetCompras();
        } catch (Exception e) {
            compras = new LinkedList<IMovimento>();
        }
        return compras;
    }

    public Double CalcularRendimento(IAtivo ativo, IMovimentacao movimentacao) {
        BigDecimal soma = BigDecimal.ZERO;
        BigDecimal contador = BigDecimal.ZERO;
        BigDecimal precoMercado = BigDecimal.valueOf(ativo.GetValor());
        BigDecimal resultado = BigDecimal.ZERO;
        
        if (TratarListaCompras(movimentacao).isEmpty()) {
            return 0d;
        }

        for (IMovimento movimento : movimentacao.GetCompras()) {
            if (movimento.GetNome().equals(ativo.GetNome())) {
                BigDecimal preco = BigDecimal.valueOf(movimento.GetPreco());
                BigDecimal quantidade = BigDecimal.valueOf(movimento.GetQuantidade());
                soma = soma.add(preco.multiply(quantidade));
                contador = contador.add(quantidade);
            }
        }

        resultado = TentarCalcularRendimento(soma, contador, precoMercado);
        return resultado.doubleValue();
    }

    private BigDecimal TentarCalcularRendimento(BigDecimal soma, BigDecimal contador, BigDecimal precoMercado)
            throws ArithmeticException {
        BigDecimal precoMedioCompras;
        BigDecimal resultado = BigDecimal.ZERO;
        try {
            precoMedioCompras = soma.divide(contador, 8, RoundingMode.DOWN);
            resultado = precoMercado.divide(precoMedioCompras, 8, RoundingMode.DOWN);
        } catch (ArithmeticException e) {
            System.out.println(e);
            throw e;
        }
        return resultado;
    }

    public Double CalcularValorMercadoTotal(IAtivo ativo, Double quantidade) {
        BigDecimal valorDecimal = new BigDecimal(String.valueOf(ativo.GetValor()));
        BigDecimal quantidadeDecimal = new BigDecimal(String.valueOf(quantidade));
        BigDecimal valorTotal = valorDecimal.multiply(quantidadeDecimal);
        return valorTotal.doubleValue();
    }

    public Double CalcularQuantidade(IAtivo ativo, IMovimentacao movimentacao) {
        try {
            Double quantidade = movimentacao.GetQuantidadeAtivos(ativo);
            return quantidade;
        } catch (Exception e) {
            return 0d;
        }
    }

    public IllegalArgumentException LancarCriarRegistroExcecao() {
        IllegalArgumentException e = new IllegalArgumentException("Nao foi possivel criar registro");
        return e;
    }
}
