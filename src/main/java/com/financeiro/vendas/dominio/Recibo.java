package com.financeiro.vendas.dominio;

import java.time.LocalDateTime;
import java.util.Objects;

public record Recibo(Cliente cliente, double subtotal, double impostos, double frete, LocalDateTime data) {

    public Recibo {
        Objects.requireNonNull(cliente, "Cliente obrigatorio");
        Objects.requireNonNull(data, "Data obrigatoria");
    }

    public double total() {
        return subtotal + impostos + frete;
    }
}
