package com.financeiro.vendas.servico;

import com.financeiro.vendas.dominio.Recibo;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class FormatadorRecibo {

    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public String formatar(Recibo recibo) {
        return String.join("\n",
                "=====================================",
                "RECIBO DE VENDA",
                "=====================================",
                "Data: " + DATA.format(recibo.data()),
                "Cliente: " + recibo.cliente().nome(),
                "Documento: " + recibo.cliente().documento(),
                "-------------------------------------",
                "Subtotal Itens: R$ " + moeda(recibo.subtotal()),
                "Total Impostos: R$ " + moeda(recibo.impostos()),
                "Frete: R$ " + moeda(recibo.frete()),
                "-------------------------------------",
                "TOTAL A PAGAR: R$ " + moeda(recibo.total()),
                "=====================================");
    }

    private static String moeda(double valor) {
        return String.format(Locale.US, "%.2f", valor);
    }
}
