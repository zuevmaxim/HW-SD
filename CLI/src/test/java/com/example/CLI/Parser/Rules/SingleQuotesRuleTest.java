package com.example.CLI.Parser.Rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleQuotesRuleTest {

    @Test
    void isMatching_EmptyString_True() {
        var rule = new SingleQuotesRule();
        assertTrue(rule.isMatching("\'\'"));
    }

    @Test
    void isMatching_SimpleString_True() {
        var rule = new SingleQuotesRule();
        assertTrue(rule.isMatching("\'hehe, boy\'"));
    }

    @Test
    void isMatching_StringWithThreeQuotes_False() {
        var rule = new SingleQuotesRule();
        assertFalse(rule.isMatching("\'\'\'"));
    }

    @Test
    void isMatching_StringWithFourQuotes_False() {
        var rule = new SingleQuotesRule();
        assertFalse(rule.isMatching("\'\'\'\'"));
    }

    @Test
    void isMatching_StringWithQuotesInQuotes_False() {
        var rule = new SingleQuotesRule();
        assertFalse(rule.isMatching("\'\\\'\'"));
    }
}