package com.financeiro.vendas.estrategia;

import com.financeiro.vendas.dominio.RegrasComerciais;
import com.financeiro.vendas.dominio.TipoCliente;

import java.util.EnumMap;
import java.util.Map;

/**
 * Contexto do Strategy: escolhe o algoritmo de frete conforme o tipo de cliente.
 */
public final class SeletorEstrategiaFrete {

    private final Map<TipoCliente, EstrategiaFrete> estrategias;

    public SeletorEstrategiaFrete() {
        estrategias = new EnumMap<>(TipoCliente.class);
        estrategias.put(TipoCliente.VIP, new FreteIsento());
        estrategias.put(TipoCliente.COMUM, new FretePorRegiao());
        estrategias.put(TipoCliente.OUTROS, new FreteFixo(RegrasComerciais.FRETE_REGIAO_DISTANTE));
    }

    public EstrategiaFrete selecionar(TipoCliente tipo) {
        return estrategias.get(tipo);
    }
}
