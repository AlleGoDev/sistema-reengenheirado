package com.financeiro.vendas.dominio;

import java.util.Objects;

public record Cliente(String nome, String documento, TipoCliente tipo, Endereco endereco) {

    public Cliente {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome obrigatorio");
        }
        Objects.requireNonNull(tipo, "Tipo de cliente obrigatorio");
        Objects.requireNonNull(endereco, "Endereco obrigatorio");
    }
}
