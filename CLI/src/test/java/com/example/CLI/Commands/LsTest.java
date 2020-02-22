package com.example.CLI.Commands;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LsTest {

    @Test
    void listDirectoryContents() throws IOException {
        var files = Ls.listDirectoryContents(Pwd.currentDirectory());
        assertTrue(files.contains("src"));
        assertTrue(files.contains("build.gradle"));
    }

    @Test
    void listDirectoryContentsUp() throws IOException {
        var files = Ls.listDirectoryContents("..");
        assertTrue(files.contains("CLI"));
    }

    @Test
    void listDirectoryContentsDown() throws IOException {
        var files = Ls.listDirectoryContents("src");
        assertTrue(files.contains("main"));
    }


}
