package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.SimpleContext;
import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class WeakQuotingTest {

    private Context context;

    @BeforeEach
    void init() {
        context = new SimpleContext(new HashMap<>(), (name) -> new External(name, new SimpleInformant()));
    }

    @Test
    void execute_SimpleString_CorrectOutputNoErrors() {
        var quotes = new WeakQuoting(context, "Diamonds are the girls best friend");

        var result = quotes.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("Diamonds are the girls best friend", result.getOutput().get(0));
    }

    @Test
    void execute_StringWithDollars_CorrectOutputNoErrors() {
        context.setValue("x", new Literal("ec"));
        context.setValue("y", new Literal("ho"));
        var quotes = new WeakQuoting(context, "$x$y");

        var result = quotes.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("echo", result.getOutput().get(0));
    }

    @Test
    void execute_StringWithQuotes_CorrectOutputNoErrors() {
        var quotes = new WeakQuoting(context, "\\\"");

        var result = quotes.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("\"", result.getOutput().get(0));
    }
}