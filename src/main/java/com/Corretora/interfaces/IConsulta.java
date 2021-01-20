package com.corretora.interfaces;

import java.util.ArrayList;

public interface IConsulta {
    public ArrayList<IRegistro> GetRegistroPorAtivo(IAtivo ativo);
    public void CriarRegistro(IAtivo ativo, IMovimentacao movimentacao);
}
