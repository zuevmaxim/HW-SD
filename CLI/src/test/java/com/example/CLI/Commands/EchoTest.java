package com.example.CLI.Commands;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EchoTest {

    @Test
    void execute_NoArguments_OneEmptyLineNoErrors() {
        var echo = new Echo();

        var result = echo.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("", result.getOutput().get(0));
    }

    @Test
    void execute_TwoArguments_CorrectInputNoErrors() {
        var echo = new Echo();
        var args = new ArrayList<Operation>();
        args.add(new Literal("echo"));
        args.add(new Literal("echo"));
        echo.setArgs(args);

        var result = echo.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("echo echo", result.getOutput().get(0));
    }
}