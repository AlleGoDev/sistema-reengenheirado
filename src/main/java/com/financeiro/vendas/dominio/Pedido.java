package com.financeiro.vendas.dominio;

import java.util.List;
import java.util.Objects;

public record Pedido(Cliente cliente, List<ItemVenda> itens) {

    public Pedido {
        Objects.requireNonNull(cliente, "Cliente obrigatorio");
        Objects.requireNonNull(itens, "Itens obrigatorios");
        itens = List.copyOf(itens);
    }
}
