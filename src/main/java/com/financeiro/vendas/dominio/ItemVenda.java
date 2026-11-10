package com.financeiro.vendas.dominio;

public record ItemVenda(double precoUnitario, int quantidade) {

    public ItemVenda {
        if (Double.isNaN(precoUnitario) || precoUnitario < 0) {
            throw new IllegalArgumentException("Preco unitario invalido");
        }
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade invalida");
        }
    }

    public double subtotal() {
        return precoUnitario * quantidade;
    }
}
