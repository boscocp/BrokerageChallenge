package com.Corretora;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Saldo implements ISaldo {
    /**
     *
     */
    private static final int CASASDECIMAIS = 2;
    protected Double saldo = 0d;

    public void LancarEntrada(Double valor, String string) {
        try {
            TentarLancarEntrada(valor);          
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            throw e;
        }
    }
    
    public void LancarSaida(Double valor, String string) {
        try {
            TentarLancarSaida(valor);            
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public Double Consultar() {
        return saldo;
    }

    public void TentarLancarEntrada (Double valor) throws IllegalArgumentException {
        if (!ValidarLancamento(valor)) {
            throw ValorNegativoExcecao();
        } else {
            BigDecimal a = new BigDecimal(String.valueOf(saldo));
            BigDecimal b = new BigDecimal(String.valueOf(valor));
            saldo = a.add(b).doubleValue();
            saldo = Arredondar(saldo);
        }
    }

    public void TentarLancarSaida (Double valor) throws IllegalArgumentException {
        if (!ValidarLancamento(valor)) {
            throw ValorNegativoExcecao();
        } else if (!ValidarSaldo(valor)){
            throw SaldoInsuficienteExcecao();
        } else {
            BigDecimal a = new BigDecimal(String.valueOf(saldo));
            BigDecimal b = new BigDecimal(String.valueOf(valor));
            saldo = a.subtract(b).doubleValue();
            saldo = Arredondar(saldo);
        }
    }

    public double Arredondar(double valor) {
        BigDecimal valorAux = BigDecimal.valueOf(valor);
        valorAux = valorAux.setScale(CASASDECIMAIS, RoundingMode.DOWN);
        return valorAux.doubleValue();
    }

    public boolean ValidarLancamento(Double valor) {
        if (valor <= 0d) {
            return false;
        } else {
            return true;
        }
    }

    public boolean ValidarSaldo(Double valor) {
        BigDecimal valorDecimal = new BigDecimal(String.valueOf(valor));
        BigDecimal saldoBigDecimal = BigDecimal.valueOf(saldo);
        if (saldoBigDecimal.subtract(valorDecimal).compareTo(BigDecimal.ZERO) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public IllegalArgumentException ValorNegativoExcecao() {
        IllegalArgumentException e = new IllegalArgumentException("Numero menor ou igual a zero");
        return e;
    }

    private IllegalArgumentException SaldoInsuficienteExcecao() {
        IllegalArgumentException e = new IllegalArgumentException("Saldo insuficiente");
        return e;
    }

}
