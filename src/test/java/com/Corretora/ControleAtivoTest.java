package com.corretora;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.corretora.ControleAtivos;
import com.corretora.enums.Tipo;
import com.corretora.interfaces.IControleAtivos;

import org.junit.jupiter.api.Test;

public class ControleAtivoTest {
    @Test
    public void CriarAtivoExistenteTest() {
        IControleAtivos controle = new ControleAtivos();
        String mensagemEsperada = "Ativo já existe";
        try{
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
        } catch (IllegalArgumentException e) {            
            assertTrue(e.getMessage().equals(mensagemEsperada));
        }
    }

    @Test
    public void CriarAtivoTest() {
        IControleAtivos controle = new ControleAtivos();
        
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);           
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
        
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            assertTrue(controle.GetAtivo("YDUQ3").GetNome().equals("YDUQ3"));            
    }

    @Test
    public void GetAtivosTest() {
        IControleAtivos controle = new ControleAtivos();
        
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Criar("RBRF11", 111.52d, Tipo.FUNDO);
            assertTrue(controle.GetAtivos().size() == 2);
            assertTrue(controle.GetAtivos().get(0).GetNome() == "YDUQ3");
            assertTrue(controle.GetAtivos().get(1).GetNome() == "RBRF11");            
    }

    @Test
    public void DeletarTest() {
        IControleAtivos controle = new ControleAtivos();
        
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Deletar("YDUQ3");                    
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
        
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Atualizar("YDUQ3", 53.14d, Tipo.RV);                        
            assertTrue(controle.GetAtivos().get(0).GetValor() == 53.14d);
    }

    @Test
    public void AtualizarTipoTest() {
        IControleAtivos controle = new ControleAtivos();
        
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Atualizar("YDUQ3", 23.14d, Tipo.FUNDO);            
            assertTrue(controle.GetAtivos().get(0).GetTipo().equals(Tipo.FUNDO));
    }

    @Test
    public void AtualizarNomeTest() {
        IControleAtivos controle = new ControleAtivos();
        
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            controle.Atualizar("YDUQ3", "YDUQ2");
            assertTrue(controle.GetAtivos().get(0).GetNome() == "YDUQ2");
    }

    @Test
    public void ValidarValorTest() {
        ControleAtivos controle = new ControleAtivos();
        assertTrue(controle.ValidarValor(8d));
        assertTrue(controle.ValidarValor(8.12345678d));
        assertFalse(controle.ValidarValor(8.123456789d));
    }
}
