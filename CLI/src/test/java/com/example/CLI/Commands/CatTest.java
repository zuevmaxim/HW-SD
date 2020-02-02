package com.example.CLI.Commands;

import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CatTest {

    private SimpleInformant informant;
    private String testFile = "src/test/resources/testfile.txt";

    @BeforeEach
    void init() {
        informant = new SimpleInformant();
    }

    @Test
    void execute_OneFile_CorrectOutputNoErrors() throws IOException {
        var cat = new Cat(informant);
        cat.setArgs(new ArrayList<>(Collections.singletonList(new Literal(testFile))));

        var result = cat.execute();
        assertEquals(0, result.getErrors().size());
        assertEquals(3, result.getOutput().size());

        var reader = new BufferedReader(new FileReader(new File(testFile)));
        for (var line: result.getOutput()) {
            assertEquals(line, reader.readLine());
        }
    }

    @Test
    void execute_TwoFiles_CorrectOutputNoErrors() throws IOException {
        var cat = new Cat(informant);
        var args = new ArrayList<Operation>();
        args.add(new Literal(testFile));
        args.add(new Literal(testFile));
        cat.setArgs(args);

        var result = cat.execute();
        assertEquals(0, result.getErrors().size());
        assertEquals(6, result.getOutput().size());

        var reader = new BufferedReader(new FileReader(new File(testFile)));
        for (int i = 0; i < 3; i++) {
            assertEquals(result.getOutput().get(i), reader.readLine());
            assertEquals(result.getOutput().get(i), result.getOutput().get(i + 3));
        }
    }

    @Test
    void execute_NoFiles_NoOutputErrorMessage() {
        var cat = new Cat(informant);

        var result = cat.execute();
        assertEquals(0, result.getOutput().size());
        assertEquals(1, result.getErrors().size());
    }

    @Test
    void execute_ConnectionNoFiles_CorrectOutputNoErrors() throws IOException {
        var conn = informant.createConnection();
        informant.send(conn, "Read it, BOY".getBytes());
        var cat = new Cat(informant);
        cat.addConnection(conn);

        var result = cat.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("Read it, BOY", result.getOutput().get(0));
    }

    @Test
    void execute_ConnectionOneFile_ConnectionIgnored() throws IOException {
        var conn = informant.createConnection();
        informant.send(conn, "Aaaand it's gone".getBytes());
        var cat = new Cat(informant);
        cat.addConnection(conn);
        cat.setArgs(new ArrayList<>(Collections.singletonList(new Literal(testFile))));

        var result = cat.execute();
        assertEquals(0, result.getErrors().size());
        assertEquals(3, result.getOutput().size());
    }
}