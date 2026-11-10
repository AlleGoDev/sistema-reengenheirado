package com.financeiro.vendas.servico;

import com.financeiro.vendas.dominio.RegrasComerciais;

public final class ValidadorDocumento {

    public boolean ehValido(String documento) {
        return documento != null && documento.length() == RegrasComerciais.TAMANHO_DOCUMENTO;
    }
}
