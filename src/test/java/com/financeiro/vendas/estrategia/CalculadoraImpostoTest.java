package com.financeiro.vendas.estrategia;

import com.financeiro.vendas.dominio.ItemVenda;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculadoraImpostoTest {

    private final CalculadoraImposto calculadora = new CalculadoraImposto();

    @Test
    void precoAcimaDeCemUsaAliquotaDeQuinzePorCento() {
        assertEquals(22.5, calculadora.calcular(new ItemVenda(150.0, 1)), 0.001);
        assertEquals(15.0015, calculadora.calcular(new ItemVenda(100.01, 1)), 0.0001);
    }

    @Test
    void precoDeCemOuMenosUsaAliquotaDeCincoPorCento() {
        assertEquals(5.0, calculadora.calcular(new ItemVenda(100.0, 1)), 0.001);
        assertEquals(5.0, calculadora.calcular(new ItemVenda(50.0, 2)), 0.001);
        assertEquals(0.0, calculadora.calcular(new ItemVenda(0.0, 4)), 0.001);
    }

    @Test
    void rejeitaListaDeEstrategiasVazia() {
        assertThrows(IllegalArgumentException.class, () -> new CalculadoraImposto(List.of()));
        assertThrows(IllegalArgumentException.class, () -> new CalculadoraImposto(null));
    }

    @Test
    void falhaQuandoNenhumaEstrategiaSeAplica() {
        CalculadoraImposto semCobertura = new CalculadoraImposto(List.of(
                new EstrategiaImposto() {
                    @Override
                    public boolean aplicavel(double precoUnitario) {
                        return false;
                    }

                    @Override
                    public double calcular(double subtotal) {
                        return 0;
                    }
                }));

        IllegalStateException erro = assertThrows(
                IllegalStateException.class,
                () -> semCobertura.calcular(new ItemVenda(10.0, 1)));
        assertTrue(erro.getMessage().contains("Nenhuma estrategia"));
    }
}
