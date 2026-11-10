package com.financeiro.vendas.estrategia;

import com.financeiro.vendas.dominio.Endereco;

/**
 * Strategy (GoF): familia de algoritmos de frete intercambiaveis.
 */
public interface EstrategiaFrete {

    double calcular(Endereco endereco);
}
