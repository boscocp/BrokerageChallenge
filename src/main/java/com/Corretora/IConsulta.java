package com.Corretora;

import java.util.ArrayList;

interface IConsulta {
    public ArrayList<IRegistro> GetRegistroPorAtivo(IAtivo ativo);
    public void CriarRegistro(IAtivo ativo, IMovimentacao movimentacao);
}
