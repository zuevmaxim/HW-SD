package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.SimpleContext;
import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ExtractTest {

    private Context context;

    @BeforeEach
    void init() {
        context = new SimpleContext(new HashMap<>(), (name) -> new External(name, new SimpleInformant()));
    }

    @Test
    void execute_ExistingVariable_CorrectValue() {
        context.setValue("kinder_egg", new Literal("surprise"));
        var variable = new Extract(context, "kinder_egg");

        var result = variable.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("surprise", result.getOutput().get(0));
    }

    @Test
    void execute_UnknownVariable_EmptyValue() {
        var variable = new Extract(context, "PATH");

        var result = variable.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("", result.getOutput().get(0));
    }
}