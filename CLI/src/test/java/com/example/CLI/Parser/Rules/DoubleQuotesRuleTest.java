package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.External;
import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.SimpleContext;
import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DoubleQuotesRuleTest {

    private Context context;

    @BeforeEach
    void init() {
        context = new SimpleContext(new HashMap<>(), (name) -> new External(name, new SimpleInformant()));
    }

    @Test
    void isMatching_EmptyString_True() {
        var rule = new DoubleQuotesRule(context);
        assertTrue(rule.isMatching("\"\""));
    }

    @Test
    void isMatching_SimpleString_True() {
        var rule = new DoubleQuotesRule(context);
        assertTrue(rule.isMatching("\"hehe, boy\""));
    }

    @Test
    void isMatching_StringWithThreeQuotes_False() {
        var rule = new DoubleQuotesRule(context);
        assertFalse(rule.isMatching("\"\"\""));
    }

    @Test
    void isMatching_StringWithFourQuotes_False() {
        var rule = new DoubleQuotesRule(context);
        assertFalse(rule.isMatching("\"\"\"\""));
    }

    @Test
    void isMatching_StringWithQuotesInQuotes_True() {
        var rule = new DoubleQuotesRule(context);
        assertTrue(rule.isMatching("\"\\\"\""));
    }
}