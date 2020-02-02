package com.example.CLI.Commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LiteralTest {

    @Test
    void execute_PutString_SameStringNoErrors() {
        var literal = new Literal("!@#$%^&*");

        var result = literal.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("!@#$%^&*", result.getOutput().get(0));
    }
}