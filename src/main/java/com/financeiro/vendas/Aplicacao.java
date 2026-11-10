package com.financeiro.vendas;

import com.financeiro.vendas.dominio.Cliente;
import com.financeiro.vendas.dominio.Endereco;
import com.financeiro.vendas.dominio.ItemVenda;
import com.financeiro.vendas.dominio.Pedido;
import com.financeiro.vendas.dominio.TipoCliente;
import com.financeiro.vendas.servico.ProcessadorDeVendas;

import java.util.List;

/**
 * Cenario de referencia do processador legado: cliente comum em SP.
 */
public final class Aplicacao {

    private Aplicacao() {
    }

    public static void main(String[] args) {
        Pedido pedido = new Pedido(
                new Cliente(
                        "João Silva",
                        "12345678901",
                        TipoCliente.COMUM,
                        new Endereco("Rua das Flores, 123 - SP")),
                List.of(
                        new ItemVenda(50.0, 2),
                        new ItemVenda(150.0, 1),
                        new ItemVenda(30.0, 3)));

        new ProcessadorDeVendas().processar(pedido);
    }
}
