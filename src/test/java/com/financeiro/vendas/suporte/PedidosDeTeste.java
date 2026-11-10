package com.financeiro.vendas.suporte;

import com.financeiro.vendas.dominio.Cliente;
import com.financeiro.vendas.dominio.Endereco;
import com.financeiro.vendas.dominio.ItemVenda;
import com.financeiro.vendas.dominio.Pedido;
import com.financeiro.vendas.dominio.TipoCliente;

import java.util.List;

public final class PedidosDeTeste {

    private PedidosDeTeste() {
    }

    public static Pedido referencia(TipoCliente tipo, String documento, String endereco) {
        return new Pedido(
                new Cliente("João Silva", documento, tipo, new Endereco(endereco)),
                List.of(
                        new ItemVenda(50.0, 2),
                        new ItemVenda(150.0, 1),
                        new ItemVenda(30.0, 3)));
    }
}
