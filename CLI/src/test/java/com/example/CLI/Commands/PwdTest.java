package com.example.CLI.Commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PwdTest {

    @Test
    void execute_CorrectDirectoryNoErrors() {
        var pwd = new Pwd();

        var result = pwd.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals(System.getProperty("user.dir"), result.getOutput().get(0));
    }

}