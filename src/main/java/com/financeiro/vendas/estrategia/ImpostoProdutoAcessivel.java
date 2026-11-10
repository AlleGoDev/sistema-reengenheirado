package com.financeiro.vendas.estrategia;

import com.financeiro.vendas.dominio.RegrasComerciais;

public final class ImpostoProdutoAcessivel implements EstrategiaImposto {

    @Override
    public boolean aplicavel(double precoUnitario) {
        return precoUnitario <= RegrasComerciais.LIMITE_PRODUTO_CARO;
    }

    @Override
    public double calcular(double subtotal) {
        return subtotal * RegrasComerciais.ALIQUOTA_PRODUTO_ACESSIVEL;
    }
}
