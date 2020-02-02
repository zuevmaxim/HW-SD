package com.example.CLI.Commands;

import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ExternalTest {

    private SimpleInformant informant;

    @BeforeEach
    void init() {
        informant = new SimpleInformant();
    }

    @Test
    void execute_GitHelp_SomeOutputNoError() {
        var external = new External("git", informant);
        external.setArgs(new ArrayList<>(Collections.singletonList(new Literal("--help"))));

        var result = external.execute();
        assertTrue(result.getOutput().size() > 0);
        assertEquals(0, result.getErrors().size());
    }
}