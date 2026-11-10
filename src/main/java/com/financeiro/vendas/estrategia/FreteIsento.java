package com.financeiro.vendas.estrategia;

import com.financeiro.vendas.dominio.Endereco;

public final class FreteIsento implements EstrategiaFrete {

    @Override
    public double calcular(Endereco endereco) {
        return 0.0;
    }
}
