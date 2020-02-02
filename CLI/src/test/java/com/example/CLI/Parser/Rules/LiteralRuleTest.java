package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.External;
import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.SimpleContext;
import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LiteralRuleTest {

    private Context context;

    @BeforeEach
    void init() {
        context = new SimpleContext(new HashMap<>(), (name) -> new External(name, new SimpleInformant()));
    }

    @Test
    void isMatching_OneWord_True() {
        var rule = new LiteralRule(context);
        assertTrue(rule.isMatching(" sвdвfвosвыаыkjd\"\"'''f*()!@~#$sp&^6od3456f "));
    }

    @Test
    void isMatching_TwoWords_False() {
        var rule = new LiteralRule(context);
        assertFalse(rule.isMatching(" echo echo "));
    }
}