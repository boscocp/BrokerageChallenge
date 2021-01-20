package com.corretora;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.corretora.enums.Tipo;
import com.corretora.interfaces.IContaCorrente;
import com.corretora.interfaces.IControleAtivos;
import com.corretora.interfaces.IMovimentacao;

import org.junit.jupiter.api.Test;

public class ConsultaTest {
    @Test
    public void CalcularQuantidadeTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(300d, "");
        controle.Criar("YDUQ3", 23.14d, Tipo.RV);
        movimentacao.ComprarAtivos(controle.GetAtivo("YDUQ3"), 23.14d, 10d, conta);
        assertTrue(consulta.CalcularQuantidade(controle.GetAtivo("YDUQ3"), movimentacao) == 10d);

    }

    @Test
    public void CalcularQuantidadeZeroTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();

        conta.GetSaldo().LancarEntrada(300d, "");
        controle.Criar("YDUQ3", 23.14d, Tipo.RV);
        Consulta consulta = new Consulta();
        assertTrue(consulta.CalcularQuantidade(controle.GetAtivo("YDUQ3"), movimentacao) == 0d);

    }

    @Test
    public void CalcularValorMercadoTotalTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(300d, "");
        controle.Criar("YDUQ3", 23.14d, Tipo.RV);
        movimentacao.ComprarAtivos(controle.GetAtivo("YDUQ3"), 23.14d, 10d, conta);
        assertTrue(consulta.CalcularValorMercadoTotal(controle.GetAtivo("YDUQ3"), 10d) == 231.4d);

    }

    @Test
    public void CalcularValorMercadoTotalZeroTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();

        conta.GetSaldo().LancarEntrada(300d, "");
        controle.Criar("YDUQ3", 23.14d, Tipo.RV);
        Consulta consulta = new Consulta();
        assertTrue(consulta.CalcularValorMercadoTotal(controle.GetAtivo("YDUQ3"), 0d) == 0d);

    }

    @Test
    public void CalcularRendimentoVariasComprasTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(10000d, "");
        controle.Criar("YDUQ3", 23.14d, Tipo.RV);
        controle.Criar("RBRF11", 23.14d, Tipo.RV);
        controle.Criar("SEER3", 23.14d, Tipo.RV);
        movimentacao.ComprarAtivos(controle.GetAtivo("SEER3"), 23.14d, 10d, conta);
        movimentacao.ComprarAtivos(controle.GetAtivo("YDUQ3"), 23.14d, 10d, conta);
        movimentacao.ComprarAtivos(controle.GetAtivo("RBRF11"), 13.14d, 10d, conta);
        movimentacao.ComprarAtivos(controle.GetAtivo("RBRF11"), 34.19d, 15d, conta);
        movimentacao.ComprarAtivos(controle.GetAtivo("RBRF11"), 44.58d, 25d, conta);
        controle.GetAtivo("RBRF11").SetValor(55.38d);
        assertTrue(consulta.CalcularRendimento(controle.GetAtivo("RBRF11"), movimentacao) == 1.57441364d);

    }

    @Test
    public void CalcularRendimentoTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(10000d, "");
        controle.Criar("RBRF11", 23.14d, Tipo.RV);
        movimentacao.ComprarAtivos(controle.GetAtivo("RBRF11"), 10d, 10d, conta);
        controle.GetAtivo("RBRF11").SetValor(50d);
        assertTrue(consulta.CalcularRendimento(controle.GetAtivo("RBRF11"), movimentacao) == 5d);

    }

    @Test
    public void CalcularRendimentoZeroTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(10000d, "");
        controle.Criar("RBRF11", 23.14d, Tipo.RV);
        assertTrue(consulta.CalcularRendimento(controle.GetAtivo("RBRF11"), movimentacao) == 0d);

    }

    @Test
    public void CalcularLucroPositivoTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(10000d, "");
        controle.Criar("RBRF11", 23.14d, Tipo.RV);
        movimentacao.ComprarAtivos(controle.GetAtivo("RBRF11"), 10d, 10d, conta);
        movimentacao.VenderAtivos(controle.GetAtivo("RBRF11"), 20d, 10d, conta);
        assertTrue(consulta.CalcularLucro(controle.GetAtivo("RBRF11"), movimentacao) == 100d);

    }

    @Test
    public void CalcularLucroZeroTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(10000d, "");
        controle.Criar("RBRF11", 23.14d, Tipo.RV);
        assertTrue(consulta.CalcularLucro(controle.GetAtivo("RBRF11"), movimentacao) == 0d);

    }

    @Test
    public void CalcularLucroMultiplasComprasTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(10000d, "");
        controle.Criar("RBRF11", 23.14d, Tipo.RV);
        movimentacao.ComprarAtivos(controle.GetAtivo("RBRF11"), 10d, 10d, conta);
        movimentacao.ComprarAtivos(controle.GetAtivo("RBRF11"), 12.34d, 10d, conta);
        movimentacao.ComprarAtivos(controle.GetAtivo("RBRF11"), 15.34578d, 10d, conta);
        movimentacao.VenderAtivos(controle.GetAtivo("RBRF11"), 18.19d, 30d, conta);
        assertTrue(consulta.CalcularLucro(controle.GetAtivo("RBRF11"), movimentacao) == 168.8422d);

    }

    @Test
    public void CalcularLucroNegativoComprasTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(10000d, "");
        controle.Criar("RBRF11", 23.14d, Tipo.RV);
        movimentacao.ComprarAtivos(controle.GetAtivo("RBRF11"), 10d, 10d, conta);
        movimentacao.VenderAtivos(controle.GetAtivo("RBRF11"), 8d, 10d, conta);
        assertTrue(consulta.CalcularLucro(controle.GetAtivo("RBRF11"), movimentacao) == -20d);

    }

    @Test
    public void CalcularLucroSemMovimentoComprasTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(10000d, "");
        controle.Criar("RBRF11", 23.14d, Tipo.RV);
        assertTrue(consulta.CalcularLucro(controle.GetAtivo("RBRF11"), movimentacao) == 0d);

    }

    @Test
    public void CriarRegistroVazioTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(10000d, "");
        controle.Criar("RBRF11", 23.14d, Tipo.RV);
        consulta.CriarRegistro(controle.GetAtivo("RBRF11"), movimentacao);
        assertTrue(consulta.GetRegistroPorAtivo(controle.GetAtivo("RBRF11")).size() == 1);

    }

    @Test
    public void CriarVariosRegistrosVaziosTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(10000d, "");
        controle.Criar("RBRF11", 23.14d, Tipo.RV);
        consulta.CriarRegistro(controle.GetAtivo("RBRF11"), movimentacao);
        consulta.CriarRegistro(controle.GetAtivo("RBRF11"), movimentacao);
        consulta.CriarRegistro(controle.GetAtivo("RBRF11"), movimentacao);
        assertTrue(consulta.GetRegistroPorAtivo(controle.GetAtivo("RBRF11")).size() == 3);

    }

    @Test
    public void VerificarRegistroTest() {
        IContaCorrente conta = new ContaCorrente();
        IControleAtivos controle = new ControleAtivos();
        IMovimentacao movimentacao = new Movimentacao();
        Consulta consulta = new Consulta();

        conta.GetSaldo().LancarEntrada(10000d, "");
        controle.Criar("RBRF11", 23.14d, Tipo.RV);
        movimentacao.ComprarAtivos(controle.GetAtivo("RBRF11"), 10d, 10d, conta);
        movimentacao.ComprarAtivos(controle.GetAtivo("RBRF11"), 12.34d, 10d, conta);
        movimentacao.ComprarAtivos(controle.GetAtivo("RBRF11"), 15.34578d, 10d, conta);
        movimentacao.VenderAtivos(controle.GetAtivo("RBRF11"), 18.19d, 30d, conta);
        consulta.CriarRegistro(controle.GetAtivo("RBRF11"), movimentacao);
        assertTrue(consulta.GetRegistroPorAtivo(controle.GetAtivo("RBRF11")).size() == 1);
        assertTrue(consulta.GetRegistroPorAtivo(controle.GetAtivo("RBRF11")).get(0).GetNome() == "RBRF11");
        assertTrue(consulta.GetRegistroPorAtivo(controle.GetAtivo("RBRF11")).get(0).GetTipo() == Tipo.RV);
        assertTrue(consulta.GetRegistroPorAtivo(controle.GetAtivo("RBRF11")).get(0).GetLucro() == 168.8422d);
        assertTrue(consulta.GetRegistroPorAtivo(controle.GetAtivo("RBRF11")).get(0).GetRendimento() == 1.84207412d);
        assertTrue(consulta.GetRegistroPorAtivo(controle.GetAtivo("RBRF11")).get(0).GetValorMercadoTotal() == 0d);

    }
}
