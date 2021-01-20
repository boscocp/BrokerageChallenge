package com.corretora.exception;

public class ValorNegativoException extends IllegalArgumentException {

    public ValorNegativoException(String campo) {
        super("Valor " + campo + " precisa ser maior que zero");
    }

}
