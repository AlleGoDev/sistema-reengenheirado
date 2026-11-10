package com.financeiro.vendas.servico;

public final class SaidaConsole implements SaidaRelatorio {

    @Override
    public void escrever(String mensagem) {
        System.out.println(mensagem);
    }
}
