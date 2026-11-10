package com.financeiro.vendas.dominio;

import com.financeiro.vendas.servico.ValidadorDocumento;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DominioTest {

    @Test
    void documentoPrecisaTerOnzeCaracteres() {
        ValidadorDocumento validador = new ValidadorDocumento();
        assertTrue(validador.ehValido("12345678901"));
        assertFalse(validador.ehValido(null));
        assertFalse(validador.ehValido("1234567890"));
        assertFalse(validador.ehValido("123456789012"));
    }

    @Test
    void objetosDeDominioRecusamDadosIncompletos() {
        assertThrows(IllegalArgumentException.class, () -> new Endereco("  "));
        assertThrows(IllegalArgumentException.class, () -> new Endereco(null));
        assertThrows(IllegalArgumentException.class, () -> new ItemVenda(-1, 1));
        assertThrows(IllegalArgumentException.class, () -> new ItemVenda(Double.NaN, 1));
        assertThrows(IllegalArgumentException.class, () -> new ItemVenda(10, -1));

        Endereco endereco = new Endereco("SP");
        assertThrows(IllegalArgumentException.class,
                () -> new Cliente(" ", "12345678901", TipoCliente.VIP, endereco));
        assertThrows(NullPointerException.class,
                () -> new Cliente("Ana", "12345678901", null, endereco));
        assertThrows(NullPointerException.class,
                () -> new Cliente("Ana", "12345678901", TipoCliente.VIP, null));

        Cliente cliente = new Cliente("Ana", null, TipoCliente.VIP, endereco);
        assertThrows(NullPointerException.class, () -> new Pedido(null, List.of()));
        assertThrows(NullPointerException.class, () -> new Pedido(cliente, null));

        LocalDateTime agora = LocalDateTime.of(2026, 11, 9, 10, 0);
        assertThrows(NullPointerException.class, () -> new Recibo(cliente, 0, 0, 0, null));
        assertEquals(3.5, new Recibo(cliente, 1, 1.5, 1, agora).total(), 0.001);
    }
}
