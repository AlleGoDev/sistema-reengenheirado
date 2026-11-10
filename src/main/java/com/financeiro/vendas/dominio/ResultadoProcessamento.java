package com.financeiro.vendas.dominio;

public record ResultadoProcessamento(boolean aprovado, Recibo recibo) {

    public static ResultadoProcessamento rejeitado() {
        return new ResultadoProcessamento(false, null);
    }

    public static ResultadoProcessamento aprovado(Recibo recibo) {
        return new ResultadoProcessamento(true, recibo);
    }
}
