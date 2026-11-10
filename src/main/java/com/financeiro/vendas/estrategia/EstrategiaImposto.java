package com.financeiro.vendas.estrategia;

/**
 * Strategy (GoF): algoritmo de imposto selecionado pelo preco do item.
 */
public interface EstrategiaImposto {

    boolean aplicavel(double precoUnitario);

    double calcular(double subtotal);
}
