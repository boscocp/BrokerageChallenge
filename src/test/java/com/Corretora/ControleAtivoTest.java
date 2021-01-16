package com.Corretora;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;


import org.junit.Test;

public class ControleAtivoTest {
    @Test
    public void CriarAtivoExistenteTest() {
        IControleAtivos controle = new ControleAtivos();
        String mensagemEsperada = "Ativo já existe";
        try {
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
        } catch (IllegalArgumentException e) {            
            assertTrue(e.getMessage().equals(mensagemEsperada));
        }
    }

    @Test
    public void CriarAtivoTest() {
        IControleAtivos controle = new ControleAtivos();
        try {
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);           
        } catch (Exception e) {
            assumeNoException(e);
        }
    }

    @Test
    public void NaoExisteAtivoThrowTest() {
        ControleAtivos controle = new ControleAtivos();
        String mensagemEsperada = "Ativo inexistente";
        try {
            throw controle.LancarExcesaoSeNaoExiste();
        } catch (Exception e) {
            assertTrue(e.getMessage().equals(mensagemEsperada));
        }
    }

    @Test
    public void GetAtivoTest() {
        IControleAtivos controle = new ControleAtivos();
        try {
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            assertTrue(controle.GetAtivo("YDUQ3").GetNome().equals("YDUQ3"));            
        } catch (Exception e) {
            assumeNoException(e);
        }
    }

    @Test
    public void GetAtivosTest() {
        IControleAtivos controle = new ControleAtivos();
        try {
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Criar("RBRF11", 111.52d, Tipo.FUNDO);
            assertTrue(controle.GetAtivos().size() == 2);
            assertTrue(controle.GetAtivos().get(0).GetNome() == "YDUQ3");
            assertTrue(controle.GetAtivos().get(1).GetNome() == "RBRF11");            
        } catch (Exception e) {
            assumeNoException(e);
        }
    }

    @Test
    public void DeletarTest() {
        IControleAtivos controle = new ControleAtivos();
        try {
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Deletar("YDUQ3");            
        } catch (Exception e) {
            assumeNoException(e);
        }        
    }

    @Test
    public void DeletarInexistenteTest() {
        IControleAtivos controle = new ControleAtivos();
        String mensagemEsperada = "Ativo inexistente";
        try {
            controle.Deletar("YDUQ3");            
        } catch (Exception e) {
            assertTrue(e.getMessage().equals(mensagemEsperada));
        }
    }

    @Test
    public void AtualizarValorTest() {
        IControleAtivos controle = new ControleAtivos();
        try {
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Atualizar("YDUQ3", 53.14d, Tipo.RV);                        
            assertTrue(controle.GetAtivos().get(0).GetValor() == 53.14d);
        } catch (Exception e) {
            assumeNoException(e);
        }
    }

    @Test
    public void AtualizarTipoTest() {
        IControleAtivos controle = new ControleAtivos();
        try {
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Atualizar("YDUQ3", 23.14d, Tipo.FUNDO);            
            assertTrue(controle.GetAtivos().get(0).GetTipo().equals(Tipo.FUNDO));
        } catch (Exception e) {
            assumeNoException(e);
        }
    }

    @Test
    public void AtualizarNomeTest() {
        IControleAtivos controle = new ControleAtivos();
        try {
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Atualizar("YDUQ3", "YDUQ2");
            assertTrue(controle.GetAtivos().get(0).GetNome() == "YDUQ2");
        } catch (Exception e) {
            assumeNoException(e);
        }
    }

    @Test
    public void ValidarValorTest() {
        ControleAtivos controle = new ControleAtivos();
        assertTrue(controle.ValidarValor(8d));
        assertTrue(controle.ValidarValor(8.12345678d));
        assertFalse(controle.ValidarValor(8.123456789d));
    }
}
