package com.example.CLI.Commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CdTest {

    private String workingDirectory = "";

    @BeforeEach
    void init() {
        workingDirectory = Pwd.currentDirectory();
    }

    @AfterEach
    void returnBack() throws IOException {
        Cd.changeCurrentDirectory(workingDirectory);
    }

    @Test
    void changeCurrentDirectoryToHome() {
        new Cd().execute();
        assertEquals(Cd.getHomeDirectory(), Pwd.currentDirectory());
    }

    @Test
    void changeCurrentDirectoryUp() throws IOException {
        final var command = new Cd();
        final var args = new ArrayList<Operation>();
        args.add(new Literal(".."));
        command.setArgs(args);
        command.execute();
        final var files = Ls.listDirectoryContents(Pwd.currentDirectory());
        assertTrue(files.contains("CLI"));
    }

    @Test
    void changeCurrentDirectoryToSrc() throws IOException {
        final var command = new Cd();
        final var args = new ArrayList<Operation>();
        args.add(new Literal("src"));
        command.setArgs(args);
        command.execute();
        final var files = Ls.listDirectoryContents(Pwd.currentDirectory());
        assertTrue(files.contains("main"));
        assertTrue(files.contains("test"));
    }
}
