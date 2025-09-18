package org.caixaverso.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {

    @Test
    void testInstanciacao() {
        assertDoesNotThrow(OpenApiConfig::new);
    }
}