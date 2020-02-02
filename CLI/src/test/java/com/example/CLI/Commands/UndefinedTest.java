package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.SimpleContext;
import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class UndefinedTest {

    private SimpleInformant informant;
    private Context context;

    @BeforeEach
    void init() {
        informant = new SimpleInformant();
        var commands = new HashMap<String, Supplier<Command>>();
        commands.put("echo", Echo::new);
        context = new SimpleContext(commands, (name) -> new External(name, informant));
    }

    @Test
    void execute_ExternalCommand_SomeOutputNoErrors() {
        var command = new Undefined(context, new Literal("git"), informant);
        command.setArgs(new ArrayList<>(Collections.singletonList(new Literal("--help"))));

        var result = command.execute();
        assertTrue(result.getOutput().size() > 0);
        assertEquals(0, result.getErrors().size());
    }

    @Test
    void execute_InnerCommand_CorrectOutputNoErrors() {
        var command = new Undefined(context, new Literal("echo"), informant);
        command.setArgs(new ArrayList<>(Collections.singletonList(new Literal("echo"))));

        var result = command.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("echo", result.getOutput().get(0));
    }
}