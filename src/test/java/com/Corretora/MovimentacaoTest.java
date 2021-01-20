package com.corretora;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.corretora.ContaCorrente;
import com.corretora.ControleAtivos;
import com.corretora.Movimentacao;
import com.corretora.enums.Tipo;
import com.corretora.exception.ValorNegativoException;
import com.corretora.interfaces.IContaCorrente;
import com.corretora.interfaces.IControleAtivos;
import com.corretora.interfaces.IMovimentacao;
import com.corretora.interfaces.IMovimento;

import org.junit.jupiter.api.Test;

public class MovimentacaoTest {
    @Test
    public void ComprarAtivosTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        // try {
        conta.GetSaldo().LancarEntrada(300d, "");
        controle.Criar("YDUQ3", 23.14d, Tipo.RV);
        movimentacao.ComprarAtivos(controle.GetAtivos().get(0), 23.14d, 10d, conta);
        IMovimento movimento = movimentacao.GetCompras().get(0);
        assertTrue(
                movimento.GetNome() == "YDUQ3" && movimento.GetPreco() == 23.14d && movimento.GetQuantidade() == 10d);
        // } catch (Exception e) {
        //
        // }
    }

    @Test
    public void ComprarAtivosSemSaldoTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        Movimentacao movimentacao = new Movimentacao();
        String mensagemEsperada = "Saldo insuficiente";
        try {
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            conta.GetSaldo().LancarEntrada(300d, "");
            movimentacao.ComprarAtivos(controle.GetAtivos().get(0), 230.14d, 100d, conta);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals(mensagemEsperada));
        }
    }

    @Test
    public void ValidarQuantidadeTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        Movimentacao movimentacao = new Movimentacao();
        try {
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            conta.GetSaldo().LancarEntrada(300d, "");
            movimentacao.ComprarAtivos(controle.GetAtivos().get(0), 23.14d, 10d, conta);
            assertFalse(movimentacao.ValidarQuantidadeVenda(controle.GetAtivos().get(0), 11d));
        } catch (Exception e) {

        }
    }

    @Test
    public void GetQuantidadeAtivosTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        Movimentacao movimentacao = new Movimentacao();
        try {
            conta.GetSaldo().LancarEntrada(300d, "");
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            movimentacao.ComprarAtivos(controle.GetAtivos().get(0), 23.14d, 10d, conta);
            assertTrue(movimentacao.GetQuantidadeAtivos(controle.GetAtivos().get(0)) == 10d);
        } catch (Exception e) {

        }
    }

    @Test
    public void MultiplicarTest() {
        Movimentacao movimentacao = new Movimentacao();
        assertTrue(movimentacao.MultiplicarValorQuantidade(34.19d, 100d) == 3419d);
    }

    @Test
    public void VenderAtivosTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        Movimentacao movimentacao = new Movimentacao();
        try {
            conta.GetSaldo().LancarEntrada(1000d, "");
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            movimentacao.ComprarAtivos(controle.GetAtivo("YDUQ3"), 10d, 100d, conta);
            movimentacao.VenderAtivos(controle.GetAtivo("YDUQ3"), 34.19d, 100d, conta);
            assertTrue(conta.GetSaldo().Consultar() == 3419d);
        } catch (Exception e) {

        }
    }

    @Test
    public void VenderSemAtivosSuficienteTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        Movimentacao movimentacao = new Movimentacao();
        String mensagemEsperada = "Ativos insuficientes";
        try {
            conta.GetSaldo().LancarEntrada(1000d, "");
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            movimentacao.ComprarAtivos(controle.GetAtivo("YDUQ3"), 10d, 100d, conta);
            movimentacao.VenderAtivos(controle.GetAtivo("YDUQ3"), 34.19d, 1000d, conta);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals(mensagemEsperada));
        }
    }

    @Test
    public void ValidarVendaSemAtivoTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        Movimentacao movimentacao = new Movimentacao();
        String mensagemEsperada = "Nao foi possivel achar o ativo";
        try {
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            conta.GetSaldo().LancarEntrada(1000d, "");
            movimentacao.VenderAtivos(controle.GetAtivo("YDUQ3"), 12d, 1000d, conta);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals(mensagemEsperada));
        }
    }

    @Test
    public void ValidarVendaTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        Movimentacao movimentacao = new Movimentacao();
        try {
            conta.GetSaldo().LancarEntrada(1000d, "");
            controle.Criar("YDUQ3", 23.14d, Tipo.RV);
            movimentacao.ComprarAtivos(controle.GetAtivo("YDUQ3"), 10d, 100d, conta);
            movimentacao.ValidarVenda(controle.GetAtivo("YDUQ3"), 100d);
        } catch (Exception e) {

        }
    }

    @Test
    public void ValidarValorZeroTest() {
        Movimentacao movimentacao = new Movimentacao();
        String mensagemEsperada = "Valor preco precisa ser maior que zero";
        try {
            movimentacao.ValidarValorPositivo(0d, "preco");
        } catch (Exception e) {
            assertEquals(mensagemEsperada, e.getMessage());
        }
    }

    @Test
    public void LancarExcecaoNumeroNegativoEmValidarPositivo() {
        Movimentacao movimentacao = new Movimentacao();
        Exception e = assertThrows(ValorNegativoException.class, () -> {
            movimentacao.ValidarValorPositivo(-13d, "campo");
        });
        String mensagemEsperada = "Valor campo precisa ser maior que zero";

        assertEquals(mensagemEsperada, e.getMessage());
    }

    @Test
    public void ValidarValorPositivoTest() {
        Movimentacao movimentacao = new Movimentacao();
        try {
            movimentacao.ValidarValorPositivo(1d, "");
        } catch (Exception e) {

        }
    }

    @Test
    public void ValidarCasasDecimaisTest() {
        Movimentacao movimentacao = new Movimentacao();
        try {
            movimentacao.ValidarCasasDecimais(1.00d);
        } catch (Exception e) {

        }
    }

    @Test
    public void ValidarCasasDecimaisExceptionTest() {
        Movimentacao movimentacao = new Movimentacao();
        String mensagemEsperada = "Numero quantidade com mais que 2 casas decimais";
        try {
            movimentacao.ValidarCasasDecimais(1.001d);
        } catch (Exception e) {
            assertEquals(mensagemEsperada, e.getMessage());
        }
    }

    @Test
    public void bacoTemqueLancarExcecaoTest() {
        Movimentacao movimentacao = new Movimentacao();
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            movimentacao.Baco();
        });
        String expectedMessage = "/ by zero";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }
}
