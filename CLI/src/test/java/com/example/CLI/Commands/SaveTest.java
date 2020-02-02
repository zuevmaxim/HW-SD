package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.SimpleContext;
import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SaveTest {

    private Context context;

    @BeforeEach
    void init() {
        context = new SimpleContext(new HashMap<>(), (name) -> new External(name, new SimpleInformant()));
    }

    @Test
    void execute_SaveValue_ValueSavedNoErrors() {
        var save = new Save(context, "variable", new Literal("value"));
        save.execute();

        var result = context.getValue("variable").execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("value", result.getOutput().get(0));
    }
}