package com.financeiro.vendas.estrategia;

import com.financeiro.vendas.dominio.Endereco;
import com.financeiro.vendas.dominio.RegrasComerciais;

public final class FretePorRegiao implements EstrategiaFrete {

    @Override
    public double calcular(Endereco endereco) {
        boolean regiaoProxima = RegrasComerciais.UFS_FRETE_REDUZIDO.stream()
                .anyMatch(uf -> endereco.descricao().contains(uf));
        if (regiaoProxima) {
            return RegrasComerciais.FRETE_REGIAO_PROXIMA;
        }
        return RegrasComerciais.FRETE_REGIAO_DISTANTE;
    }
}
