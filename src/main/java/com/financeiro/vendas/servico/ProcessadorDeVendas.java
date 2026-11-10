package com.financeiro.vendas.servico;

import com.financeiro.vendas.dominio.ItemVenda;
import com.financeiro.vendas.dominio.Pedido;
import com.financeiro.vendas.dominio.Recibo;
import com.financeiro.vendas.dominio.ResultadoProcessamento;
import com.financeiro.vendas.estrategia.CalculadoraImposto;
import com.financeiro.vendas.estrategia.EstrategiaFrete;
import com.financeiro.vendas.estrategia.SeletorEstrategiaFrete;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Orquestra o pedido. Nao calcula frete nem imposto: delega as Strategies.
 */
public final class ProcessadorDeVendas {

    private final ValidadorDocumento validadorDocumento;
    private final CalculadoraImposto calculadoraImposto;
    private final SeletorEstrategiaFrete seletorFrete;
    private final FormatadorRecibo formatadorRecibo;
    private final SaidaRelatorio saida;
    private final Supplier<LocalDateTime> relogio;

    public ProcessadorDeVendas() {
        this(new ValidadorDocumento(), new CalculadoraImposto(), new SeletorEstrategiaFrete(),
                new FormatadorRecibo(), new SaidaConsole(), LocalDateTime::now);
    }

    public ProcessadorDeVendas(
            ValidadorDocumento validadorDocumento,
            CalculadoraImposto calculadoraImposto,
            SeletorEstrategiaFrete seletorFrete,
            FormatadorRecibo formatadorRecibo,
            SaidaRelatorio saida,
            Supplier<LocalDateTime> relogio) {
        this.validadorDocumento = Objects.requireNonNull(validadorDocumento);
        this.calculadoraImposto = Objects.requireNonNull(calculadoraImposto);
        this.seletorFrete = Objects.requireNonNull(seletorFrete);
        this.formatadorRecibo = Objects.requireNonNull(formatadorRecibo);
        this.saida = Objects.requireNonNull(saida);
        this.relogio = Objects.requireNonNull(relogio);
    }

    public ResultadoProcessamento processar(Pedido pedido) {
        saida.escrever("Iniciando processamento...");
        if (!validadorDocumento.ehValido(pedido.cliente().documento())) {
            saida.escrever("Erro: Documento invalido.");
            return ResultadoProcessamento.rejeitado();
        }

        double subtotal = pedido.itens().stream().mapToDouble(ItemVenda::subtotal).sum();
        double impostos = pedido.itens().stream().mapToDouble(calculadoraImposto::calcular).sum();
        EstrategiaFrete estrategiaFrete = seletorFrete.selecionar(pedido.cliente().tipo());
        double frete = estrategiaFrete.calcular(pedido.cliente().endereco());

        Recibo recibo = new Recibo(pedido.cliente(), subtotal, impostos, frete, relogio.get());
        saida.escrever(formatadorRecibo.formatar(recibo));
        return ResultadoProcessamento.aprovado(recibo);
    }
}
