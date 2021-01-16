package com.Corretora;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ControleAtivos implements IControleAtivos {

    private List<Ativo> ativos = new ArrayList<Ativo>(); // supondo que ativo usa mais get (O(1)) que add (O(n))
    private Hashtable<String, Ativo> htAtivos = new Hashtable<String, Ativo>();

    public void Criar(String nome, Double preco, Tipo tipo) {
        try {
            TentaCriar(nome, preco, tipo);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void Deletar(String nome) {
        try {
            TentaDeletar(nome);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public Ativo GetAtivo(String nome) {
        try {
            return TentarGetAtivo(nome);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void Atualizar(String nome, Double preco, Tipo tipo) {
        try {
            TentarAtualizar(nome, preco, tipo);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void Atualizar(String nome, String novoNome) {
        try {
            TentarAtualizarNome(nome, novoNome);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void Atualizar(String nome, Double preco) {
        try {
            TentarAtualizarPreco(nome, preco);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public List<Ativo> GetAtivos() {
        try {
            return TentarGetAtivos();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void TentaCriar(String nome, Double preco, Tipo tipo) throws IllegalArgumentException {
        if (htAtivos.containsKey(nome)) {
            throw LancarExcesaoSeExiste();
        } else if (!ValidarValor(preco)) {
            throw MaisDeOitoDecimaisExcecao();
        } else {
            ValidarValor(preco);
            Ativo ativo = new Ativo();
            ativo.SetNome(nome);
            ativo.SetTipo(tipo);
            ativo.SetValor(preco);
            ativos.add(ativo);
            htAtivos.put(nome, ativo);
        }
    }

    public void TentaDeletar(String nome) throws IllegalArgumentException {
        if (!htAtivos.containsKey(nome)) {
            throw LancarExcesaoSeNaoExiste();
        } else {
            ativos.remove(htAtivos.get(nome));
            htAtivos.remove(nome);
        }
    }

    public Ativo TentarGetAtivo(String nome) throws IllegalArgumentException {
        if (!htAtivos.containsKey(nome)) {
            LancarExcesaoSeNaoExiste();
            return null;
        } else {
            return htAtivos.get(nome);
        }
    }

    public void TentarAtualizar(String nome, Double preco, Tipo tipo) throws IllegalArgumentException {
        if (!htAtivos.containsKey(nome)) {
            throw LancarExcesaoSeNaoExiste();
        } else if (!ValidarValor(preco)) {
            throw MaisDeOitoDecimaisExcecao();
        } else {
            htAtivos.get(nome).SetValor(preco);
            htAtivos.get(nome).SetTipo(tipo);
        }
    }

    public void TentarAtualizarNome(String nome, String novoNome) throws IllegalArgumentException {
        if (!htAtivos.containsKey(nome)) {
            throw LancarExcesaoSeNaoExiste();
        } else {
            htAtivos.get(nome).SetNome(novoNome);
            htAtivos.put(novoNome, htAtivos.get(nome));
            htAtivos.remove(nome);
        }
    }

    public void TentarAtualizarPreco(String nome, Double preco) throws IllegalArgumentException {
        if (!ValidarValor(preco)) {
            throw MaisDeOitoDecimaisExcecao();
        } else {
            htAtivos.get(nome).SetValor(preco);
        }
    }

    public List<Ativo> TentarGetAtivos() throws NullPointerException{
        if(ativos.isEmpty()) {
            throw new NullPointerException();
        } else {
            return ativos;
        }
    }

    public boolean ValidarValor(Double valor) {
        boolean atehOitoDecimais = BigDecimal.valueOf(valor).scale() <= 8;
        if (atehOitoDecimais) {
            return true;
        } else {
            return false;
        }
    }

    public IllegalArgumentException MaisDeOitoDecimaisExcecao() {
        IllegalArgumentException e = new IllegalArgumentException("Mais que 8 casas decimais");
        return e;
    }

    public IllegalArgumentException LancarExcesaoSeNaoExiste() {
        IllegalArgumentException e = new IllegalArgumentException("Ativo inexistente");
        return e;
    }

    public IllegalArgumentException LancarExcesaoSeExiste() {
        IllegalArgumentException e = new IllegalArgumentException("Ativo já existe");
        return e;
    }

}