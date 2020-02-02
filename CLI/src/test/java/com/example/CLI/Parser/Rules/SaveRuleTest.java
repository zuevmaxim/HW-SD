package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.External;
import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.SimpleContext;
import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SaveRuleTest {

    private Context context;

    @BeforeEach
    void init() {
        context = new SimpleContext(new HashMap<>(), (name) -> new External(name, new SimpleInformant()));
    }

    @Test
    void isMatching_SimpleEquation_True() {
        var rule = new SaveRule(context);
        assertTrue(rule.isMatching("O=O"));
    }

}