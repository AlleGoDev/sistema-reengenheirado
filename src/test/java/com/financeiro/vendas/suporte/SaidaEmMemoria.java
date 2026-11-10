package com.financeiro.vendas.suporte;

import com.financeiro.vendas.servico.SaidaRelatorio;

import java.util.ArrayList;
import java.util.List;

public final class SaidaEmMemoria implements SaidaRelatorio {

    private final List<String> mensagens = new ArrayList<>();

    @Override
    public void escrever(String mensagem) {
        mensagens.add(mensagem);
    }

    public List<String> mensagens() {
        return List.copyOf(mensagens);
    }

    public String texto() {
        return String.join("\n", mensagens);
    }
}
