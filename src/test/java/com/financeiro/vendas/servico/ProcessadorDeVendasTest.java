package com.financeiro.vendas.servico;

import com.financeiro.vendas.Aplicacao;
import com.financeiro.vendas.dominio.Recibo;
import com.financeiro.vendas.dominio.ResultadoProcessamento;
import com.financeiro.vendas.dominio.TipoCliente;
import com.financeiro.vendas.estrategia.CalculadoraImposto;
import com.financeiro.vendas.estrategia.SeletorEstrategiaFrete;
import com.financeiro.vendas.suporte.PedidosDeTeste;
import com.financeiro.vendas.suporte.SaidaEmMemoria;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProcessadorDeVendasTest {

    private static final LocalDateTime AGORA = LocalDateTime.of(2026, 11, 9, 20, 15, 0);
    private static final double DELTA = 0.001;

    @Test
    void clienteComumEmSaoPauloPagaFreteReduzidoEImpostosPorFaixa() {
        SaidaEmMemoria saida = new SaidaEmMemoria();
        ResultadoProcessamento resultado = processador(saida).processar(
                PedidosDeTeste.referencia(TipoCliente.COMUM, "12345678901", "Rua das Flores, 123 - SP"));

        assertTrue(resultado.aprovado());
        Recibo recibo = resultado.recibo();
        assertEquals(340.0, recibo.subtotal(), DELTA);
        assertEquals(32.0, recibo.impostos(), DELTA);
        assertEquals(15.0, recibo.frete(), DELTA);
        assertEquals(387.0, recibo.total(), DELTA);
        assertEquals(AGORA, recibo.data());

        String texto = saida.texto();
        assertTrue(texto.contains("Iniciando processamento..."));
        assertTrue(texto.contains("Cliente: João Silva"));
        assertTrue(texto.contains("Documento: 12345678901"));
        assertTrue(texto.contains("Subtotal Itens: R$ 340.00"));
        assertTrue(texto.contains("Total Impostos: R$ 32.00"));
        assertTrue(texto.contains("Frete: R$ 15.00"));
        assertTrue(texto.contains("TOTAL A PAGAR: R$ 387.00"));
        assertTrue(texto.contains("Data: 09/11/2026 20:15:00"));
    }

    @Test
    void clienteComumNoRioEEmMinasTambemPagaFreteReduzido() {
        assertEquals(15.0, frete(TipoCliente.COMUM, "Av. Atlantica - RJ"), DELTA);
        assertEquals(15.0, frete(TipoCliente.COMUM, "Centro - MG"), DELTA);
    }

    @Test
    void clienteComumForaDoEixoEClienteDeOutroTipoPagamFreteCheio() {
        assertEquals(50.0, frete(TipoCliente.COMUM, "Rua Chile, Salvador - BA"), DELTA);
        assertEquals(50.0, frete(TipoCliente.deCodigo(9), "Rua das Flores, 123 - SP"), DELTA);
        assertEquals(50.0, frete(TipoCliente.COMUM, "rua das flores - sp"), DELTA);
    }

    @Test
    void clienteVipNaoPagaFrete() {
        assertEquals(0.0, frete(TipoCliente.VIP, "Rua das Flores, 123 - BA"), DELTA);
    }

    @Test
    void documentoInvalidoInterrompeSemEmitirRecibo() {
        SaidaEmMemoria saida = new SaidaEmMemoria();
        ResultadoProcessamento resultado = processador(saida).processar(
                PedidosDeTeste.referencia(TipoCliente.VIP, "123", "Rua das Flores, 123 - SP"));

        assertFalse(resultado.aprovado());
        assertNull(resultado.recibo());
        assertEquals(2, saida.mensagens().size());
        assertEquals("Erro: Documento invalido.", saida.mensagens().get(1));
    }

    @Test
    void pedidoSemItensGeraReciboZeradoMantendoOFrete() {
        SaidaEmMemoria saida = new SaidaEmMemoria();
        var pedido = new com.financeiro.vendas.dominio.Pedido(
                new com.financeiro.vendas.dominio.Cliente(
                        "Ana",
                        "12345678901",
                        TipoCliente.COMUM,
                        new com.financeiro.vendas.dominio.Endereco("BA")),
                java.util.List.of());

        Recibo recibo = processador(saida).processar(pedido).recibo();
        assertEquals(0.0, recibo.subtotal(), DELTA);
        assertEquals(0.0, recibo.impostos(), DELTA);
        assertEquals(50.0, recibo.frete(), DELTA);
    }

    @Test
    void aplicacaoImprimeOReciboDoCenarioDeReferencia() {
        ByteArrayOutputStream capturado = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(capturado, true, StandardCharsets.UTF_8));
        try {
            Aplicacao.main(new String[0]);
        } finally {
            System.setOut(original);
        }

        String texto = capturado.toString(StandardCharsets.UTF_8);
        assertTrue(texto.contains("RECIBO DE VENDA"));
        assertTrue(texto.contains("TOTAL A PAGAR: R$ 387.00"));
    }

    private static double frete(TipoCliente tipo, String endereco) {
        SaidaEmMemoria saida = new SaidaEmMemoria();
        return processador(saida)
                .processar(PedidosDeTeste.referencia(tipo, "12345678901", endereco))
                .recibo()
                .frete();
    }

    private static ProcessadorDeVendas processador(SaidaEmMemoria saida) {
        return new ProcessadorDeVendas(
                new ValidadorDocumento(),
                new CalculadoraImposto(),
                new SeletorEstrategiaFrete(),
                new FormatadorRecibo(),
                saida,
                () -> AGORA);
    }
}
