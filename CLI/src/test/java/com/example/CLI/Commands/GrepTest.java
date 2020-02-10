package com.example.CLI.Commands;

import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrepTest {

    private SimpleInformant informant;
    private String testFile = "src/test/resources/testfile.txt";

    @BeforeEach
    void init() {
        informant = new SimpleInformant();
    }

    @Test
    void execute_OneFile_CorrectOutputNoErrors() throws IOException {
        var grep = new Grep(informant);
        var args = new ArrayList<Operation>();
        args.add(new Literal("борода"));
        args.add(new Literal(testFile));
        grep.setArgs(args);

        var result = grep.execute();
        assertEquals(2, result.getOutput().size());
        assertEquals(0, result.getErrors().size());

        var reader = new BufferedReader(new FileReader(new File(testFile)));
        assertEquals(reader.readLine(), result.getOutput().get(0));
        reader.readLine();
        assertEquals(reader.readLine(), result.getOutput().get(1));
    }

    @Test
    void execute_TwoFiles_CorrectOutputNoErrors() throws IOException {
        var grep = new Grep(informant);
        var args = new ArrayList<Operation>();
        args.add(new Literal("борода"));
        args.add(new Literal(testFile));
        args.add(new Literal(testFile));
        grep.setArgs(args);

        var result = grep.execute();
        assertEquals(4, result.getOutput().size());
        assertEquals(0, result.getErrors().size());

        var reader = new BufferedReader(new FileReader(new File(testFile)));
        assertEquals(reader.readLine(), result.getOutput().get(0));
        assertEquals(result.getOutput().get(0), result.getOutput().get(2));
        reader.readLine();
        assertEquals(reader.readLine(), result.getOutput().get(1));
        assertEquals(result.getOutput().get(1), result.getOutput().get(3));
    }

    @Test
    void execute_NoFilesNoConnections_NoOutputOneError() {
        var grep = new Grep(informant);
        var args = new ArrayList<Operation>();
        args.add(new Literal("борода"));
        grep.setArgs(args);

        var result = grep.execute();
        assertEquals(0, result.getOutput().size());
        assertEquals(1, result.getErrors().size());
    }

    @Test
    void execute_ConnectionNoFiles_CorrectOutputNoErrors() throws IOException {
        var conn = informant.createConnection();
        informant.send(conn, "Read it, BOY".getBytes());
        var grep = new Grep(informant);
        var args = new ArrayList<Operation>();
        args.add(new Literal("it"));
        grep.setArgs(args);
        grep.addConnection(conn);

        var result = grep.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("Read it, BOY", result.getOutput().get(0));
    }

    @Test
    void execute_ConnectionOneFile_ConnectionIgnored() throws IOException {
        var conn = informant.createConnection();
        informant.send(conn, "Aaaand it's gone".getBytes());
        var grep = new Grep(informant);
        grep.addConnection(conn);
        var args = new ArrayList<Operation>();
        args.add(new Literal("Луи"));
        args.add(new Literal(testFile));
        grep.setArgs(args);

        var result = grep.execute();
        assertEquals(0, result.getErrors().size());
        assertEquals(2, result.getOutput().size());
    }

    @Test
    void execute_AOption_CorrectOutputNoErrors() throws IOException {
        var grep = new Grep(informant);
        var args = new ArrayList<Operation>();
        args.add(new Literal("-A"));
        args.add(new Literal("2"));
        args.add(new Literal("борода"));
        args.add(new Literal(testFile));
        grep.setArgs(args);

        var result = grep.execute();
        assertEquals(3, result.getOutput().size());
        assertEquals(0, result.getErrors().size());

        var reader = new BufferedReader(new FileReader(new File(testFile)));
        for (var line: result.getOutput()) {
            assertEquals(line, reader.readLine());
        }
    }

    @Test
    void execute_IOption_CorrectOutputNoErrors() throws IOException {
        var grep = new Grep(informant);
        var args = new ArrayList<Operation>();
        args.add(new Literal("-i"));
        args.add(new Literal("App"));
        args.add(new Literal("build.gradle"));
        grep.setArgs(args);

        var result = grep.execute();
        assertEquals(2, result.getOutput().size());
        assertEquals(0, result.getErrors().size());

        assertEquals("apply plugin: 'org.jetbrains.intellij'", result.getOutput().get(0));
        assertEquals("apply plugin: 'application'", result.getOutput().get(1));
    }

    @Test
    void execute_WOption_CorrectOutputNoErrors() {
        var grep = new Grep(informant);
        var args = new ArrayList<Operation>();
        args.add(new Literal("-w"));
        args.add(new Literal("бород"));
        args.add(new Literal(testFile));
        grep.setArgs(args);

        var result = grep.execute();
        assertEquals(0, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
    }

    @Test
    void execute_IWOptionsConcatenated_CorrectOutputNoErrors() throws IOException {
        var grep = new Grep(informant);
        var args = new ArrayList<Operation>();
        args.add(new Literal("-iw"));
        args.add(new Literal("AppLY"));
        args.add(new Literal("build.gradle"));
        grep.setArgs(args);

        var result = grep.execute();
        assertEquals(2, result.getOutput().size());
        assertEquals(0, result.getErrors().size());

        assertEquals("apply plugin: 'org.jetbrains.intellij'", result.getOutput().get(0));
        assertEquals("apply plugin: 'application'", result.getOutput().get(1));
    }

    @Test
    void execute_IWOptionsSeparated_CorrectOutputNoErrors() throws IOException {
        var grep = new Grep(informant);
        var args = new ArrayList<Operation>();
        args.add(new Literal("-i"));
        args.add(new Literal("-w"));
        args.add(new Literal("AppLY"));
        args.add(new Literal("build.gradle"));
        grep.setArgs(args);

        var result = grep.execute();
        assertEquals(2, result.getOutput().size());
        assertEquals(0, result.getErrors().size());

        assertEquals("apply plugin: 'org.jetbrains.intellij'", result.getOutput().get(0));
        assertEquals("apply plugin: 'application'", result.getOutput().get(1));
    }
}