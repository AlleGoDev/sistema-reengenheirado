package com.financeiro.vendas.estrategia;

import com.financeiro.vendas.dominio.Endereco;
import com.financeiro.vendas.dominio.TipoCliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EstrategiasFreteTest {

    private final SeletorEstrategiaFrete seletor = new SeletorEstrategiaFrete();

    @Test
    void selecionaAFamiliaDeAlgoritmosDoStrategy() {
        assertInstanceOf(FreteIsento.class, seletor.selecionar(TipoCliente.VIP));
        assertInstanceOf(FretePorRegiao.class, seletor.selecionar(TipoCliente.COMUM));
        assertInstanceOf(FreteFixo.class, seletor.selecionar(TipoCliente.OUTROS));
        assertEquals(TipoCliente.COMUM, TipoCliente.deCodigo(2));
        assertEquals(TipoCliente.OUTROS, TipoCliente.deCodigo(0));
        assertEquals(TipoCliente.OUTROS, TipoCliente.deCodigo(9));
        assertEquals(1, TipoCliente.VIP.codigo());
    }

    @Test
    void freteFixoRecusaValorNegativo() {
        Endereco endereco = new Endereco("SP");
        assertThrows(IllegalArgumentException.class, () -> new FreteFixo(-1).calcular(endereco));
        assertEquals(0.0, new FreteIsento().calcular(endereco), 0.001);
        assertEquals(12.5, new FreteFixo(12.5).calcular(endereco), 0.001);
    }
}
