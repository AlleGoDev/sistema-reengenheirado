package com.financeiro.vendas.dominio;

public enum TipoCliente {
    VIP(1),
    COMUM(2),
    OUTROS(0);

    private final int codigo;

    TipoCliente(int codigo) {
        this.codigo = codigo;
    }

    public int codigo() {
        return codigo;
    }

    public static TipoCliente deCodigo(int codigo) {
        for (TipoCliente tipo : values()) {
            if (tipo.codigo == codigo && tipo != OUTROS) {
                return tipo;
            }
        }
        return OUTROS;
    }
}
