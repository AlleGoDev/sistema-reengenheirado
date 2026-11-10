package com.financeiro.vendas.dominio;

public record Endereco(String descricao) {

    public Endereco {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Endereco obrigatorio");
        }
    }
}
