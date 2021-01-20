package com.corretora;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.corretora.Saldo;

import org.junit.jupiter.api.Test;

public class SaldoTest {

    @Test
    public void DeveriaRetornarZero() {
        Saldo saldo = new Saldo();
        assertTrue(0d == saldo.Consultar());
    }

    @Test
    public void LancarEntradaTeste() {
        Saldo saldo = new Saldo();

        saldo.LancarEntrada(33.55d, "");
        assertTrue(33.55d == saldo.Consultar());
        saldo.LancarEntrada(50d, "");
        assertTrue(83.55d == saldo.Consultar());

    }

    @Test
    public void LancarSaidaUnicaTeste() {
        Saldo saldo = new Saldo();

        saldo.LancarEntrada(50.33d, "");
        saldo.LancarSaida(20.3d, "");
        assertTrue(30.03d == saldo.Consultar());

    }

    @Test
    public void LancarSaidaMultiplaTeste() {
        Saldo saldo = new Saldo();

        saldo.LancarEntrada(50.33d, "");
        saldo.LancarSaida(20.3d, "");
        assertTrue(30.03d == saldo.Consultar());
        saldo.LancarSaida(30.03d, "");
        assertTrue(0d == saldo.Consultar());

    }

    @Test
    public void LancarSaidaMaiorQueSaldoTeste() {
        Saldo saldo = new Saldo();
        String mensagemEsperada = "Saldo insuficiente";
        try {
            saldo.LancarEntrada(50.33d, "");
            saldo.LancarSaida(70d, "");
        } catch (Exception e) {
            assertTrue(e.getMessage().equals(mensagemEsperada));
        }
        assertTrue(50.33d == saldo.Consultar());
    }

    @Test
    public void ValidarArredondarTeste() {
        Saldo saldo = new Saldo();
        assertTrue(saldo.Arredondar(33.339d) == 33.33d);
    }

    @Test
    public void ValidarMultiplosLancamentosTeste() {
        Saldo saldo = new Saldo();

        saldo.ValidarLancamento(50.33d);
        saldo.ValidarLancamento(50d);
        saldo.ValidarLancamento(50.1d);
        saldo.ValidarLancamento(50.11d);

    }

    @Test
    public void ValidarLancamentoThrowZeroTeste() {
        Saldo saldo = new Saldo();
        String mensagemEsperada = "Numero menor ou igual a zero";
        try {
            throw saldo.ValorNegativoExcecao();
        } catch (Exception e) {
            assertTrue(e.getMessage().equals(mensagemEsperada));
        }
    }

}
