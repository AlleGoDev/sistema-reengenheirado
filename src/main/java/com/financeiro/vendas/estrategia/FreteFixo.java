package com.financeiro.vendas.estrategia;

import com.financeiro.vendas.dominio.Endereco;

public final class FreteFixo implements EstrategiaFrete {

    private final double valor;

    public FreteFixo(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Frete nao pode ser negativo");
        }
        this.valor = valor;
    }

    @Override
    public double calcular(Endereco endereco) {
        return valor;
    }
}
