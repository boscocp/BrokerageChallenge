package com.Corretora;

import java.util.List;

interface IControleAtivos {
    public void Criar(String nome, Double preco, Tipo tipo);
    public void Deletar(String nome);
    public Ativo GetAtivo(String nome);
    public void Atualizar(String nome, Double preco, Tipo tipo);
    public void Atualizar(String nome, String novoNome);
    public void Atualizar(String nome, Double preco);
    public List<Ativo> GetAtivos();
}
