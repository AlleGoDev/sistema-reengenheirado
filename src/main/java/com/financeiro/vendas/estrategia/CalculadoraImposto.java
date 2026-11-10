package com.financeiro.vendas.estrategia;

import com.financeiro.vendas.dominio.ItemVenda;

import java.util.List;

public final class CalculadoraImposto {

    private final List<EstrategiaImposto> estrategias;

    public CalculadoraImposto() {
        this(List.of(new ImpostoProdutoCaro(), new ImpostoProdutoAcessivel()));
    }

    public CalculadoraImposto(List<EstrategiaImposto> estrategias) {
        if (estrategias == null || estrategias.isEmpty()) {
            throw new IllegalArgumentException("Informe ao menos uma estrategia de imposto");
        }
        this.estrategias = List.copyOf(estrategias);
    }

    public double calcular(ItemVenda item) {
        for (EstrategiaImposto estrategia : estrategias) {
            if (estrategia.aplicavel(item.precoUnitario())) {
                return estrategia.calcular(item.subtotal());
            }
        }
        throw new IllegalStateException("Nenhuma estrategia de imposto aplicavel ao item");
    }
}
