package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.External;
import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.SimpleContext;
import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandRuleTest {

    private SimpleInformant informant;
    private Context context;

    @BeforeEach
    void init() {
        informant = new SimpleInformant();
        context = new SimpleContext(new HashMap<>(), (name) -> new External(name, informant));
    }

    @Test
    void isMatching_OneWord_True() {
        var rule = new CommandRule(context, informant);
        assertTrue(rule.isMatching("   echo "));
    }

    @Test
    void isMatching_TwoWords_True() {
        var rule = new CommandRule(context, informant);
        assertTrue(rule.isMatching(" echo   \"  echo\""));
    }

    @Test
    void split_TwoWords_ListOfWords() {
        var rule = new CommandRule(context, informant);
        var list = rule.split(" echo   \"  echo\"");
        assertEquals(2, list.size());
        assertEquals("echo", list.get(0));
        assertEquals("\"  echo\"", list.get(1));
    }
}