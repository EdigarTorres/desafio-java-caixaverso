package org.caixaverso.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormatadorTest {

    @Test
    void testFormatarReaisValorPositivo() {
        String resultado = Formatador.formatarReais(1234.56);
        assertEquals("R$ 1.234,56", resultado);
    }

    @Test
    void testFormatarReaisValorZero() {
        String resultado = Formatador.formatarReais(0.0);
        assertEquals("R$ 0,00", resultado);
    }

    @Test
    void testFormatarReaisValorNegativo() {
        String resultado = Formatador.formatarReais(-50.75);
        assertEquals("-R$ 50,75", resultado);
    }

    @Test
    void testFormatarReaisValorGrande() {
        String resultado = Formatador.formatarReais(1000000.99);
        assertEquals("R$ 1.000.000,99", resultado);
    }

    @Test
    void testFormatarPercentual() {
        String resultado = Formatador.formatarPercentual(0.1234);
        assertEquals("12,34%", resultado);
    }

    @Test
    void testFormatarPercentualZero() {
        String resultado = Formatador.formatarPercentual(0.0);
        assertEquals("0,00%", resultado);
    }

    @Test
    void testFormatarPercentualNegativo() {
        String resultado = Formatador.formatarPercentual(-0.05);
        assertEquals("-5,00%", resultado);
    }
}