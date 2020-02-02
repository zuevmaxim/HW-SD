package com.example.CLI.Parser.Rules;

import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PipeRuleTest {

    private SimpleInformant informant;

    @BeforeEach
    void init() {
        informant = new SimpleInformant();
    }

    @Test
    void isMatching_OnePipe_True() {
        var rule = new PipeRule(informant);
        assertTrue(rule.isMatching("echo \"ho ho ho\" | cat"));
    }

    @Test
    void isMatching_TwoPipes_True() {
        var rule = new PipeRule(informant);
        assertTrue(rule.isMatching(" echo \" ho ho ho \"|cat|wc"));
    }

    @Test
    void split_OnePipe_CorrectList() {
        var rule = new PipeRule(informant);
        var list = rule.split("echo \"ho ho ho\" | cat");
        assertEquals(2, list.size());
        assertEquals("echo \"ho ho ho\" ", list.get(0));
        assertEquals(" cat", list.get(1));
    }

    @Test
    void split_TwoPipes_CorrectList() {
        var rule = new PipeRule(informant);
        var list = rule.split(" echo \" ho ho ho \"|cat|wc");
        assertEquals(2, list.size());
        assertEquals(" echo \" ho ho ho \"|cat", list.get(0));
        assertEquals("wc", list.get(1));
    }

}