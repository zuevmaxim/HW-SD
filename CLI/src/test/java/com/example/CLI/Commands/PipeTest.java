package com.example.CLI.Commands;

import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PipeTest {

    private SimpleInformant informant;

    @BeforeEach
    void init() {
        informant = new SimpleInformant();
    }

    @Test
    void execute_OnePipe_CorrectOutputNoErrors() {
        var echo = new Echo();
        echo.setArgs(new ArrayList<>(Collections.singletonList(new Literal("Wow, we're identical!"))));
        var cat = new Cat(informant);
        var pipe = new Pipe(echo, cat, informant);

        var result = pipe.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("Wow, we're identical!", result.getOutput().get(0));
    }

    @Test
    void execute_TwoPipes_CorrectOutputNoErrors() {
        var echo = new Echo();
        echo.setArgs(new ArrayList<>(Collections.singletonList(new Literal("Wow, we're identical!"))));
        var cat1 = new Cat(informant);
        var pipe1 = new Pipe(echo, cat1, informant);
        var cat2 = new Cat(informant);
        var pipe2 = new Pipe(pipe1, cat2, informant);

        var result = pipe2.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("Wow, we're identical!", result.getOutput().get(0));
    }
}