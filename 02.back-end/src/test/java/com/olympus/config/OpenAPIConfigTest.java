package com.olympus.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenAPIConfigTest {
    @InjectMocks
    OpenAPIConfig openAPIConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOctetStreamJsonConverterBean() {
        var converter = openAPIConfig.octetStreamJsonConverter();
        assertNotNull(converter);
    }

    @Test
    void testMyOpenAPIBean() {
        var openAPI = openAPIConfig.myOpenAPI();
        assertNotNull(openAPI);
    }
}