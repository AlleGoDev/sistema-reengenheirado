package com.financeiro.vendas.dominio;

import java.util.Set;

/**
 * Regras de negocio concentradas para eliminar numeros magicos espalhados.
 */
public final class RegrasComerciais {

    public static final double LIMITE_PRODUTO_CARO = 100.0;
    public static final double ALIQUOTA_PRODUTO_CARO = 0.15;
    public static final double ALIQUOTA_PRODUTO_ACESSIVEL = 0.05;
    public static final double FRETE_REGIAO_PROXIMA = 15.0;
    public static final double FRETE_REGIAO_DISTANTE = 50.0;
    public static final int TAMANHO_DOCUMENTO = 11;
    public static final Set<String> UFS_FRETE_REDUZIDO = Set.of("SP", "RJ", "MG");

    private RegrasComerciais() {
    }
}
