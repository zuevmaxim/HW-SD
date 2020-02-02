package com.example.CLI.Commands;

import com.example.CLI.Environment.SimpleInformant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WcTest {

    private SimpleInformant informant;
    private String testFile = "src/test/resources/testfile.txt";

    @BeforeEach
    void init() {
        informant = new SimpleInformant();
    }

    @Test
    void execute_OneFile_CorrectOutputNoErrors() {
        var wc = new Wc(informant);
        wc.setArgs(new ArrayList<>(Collections.singletonList(new Literal(testFile))));

        var result = wc.execute();
        assertEquals(0, result.getErrors().size());
        assertEquals(1, result.getOutput().size());
        assertEquals("3 129 1452", result.getOutput().get(0));
    }

    @Test
    void execute_TwoFiles_CorrectOutputNoErrors() {
        var wc = new Wc(informant);
        var args = new ArrayList<Operation>();
        args.add(new Literal(testFile));
        args.add(new Literal(testFile));
        wc.setArgs(args);

        var result = wc.execute();
        assertEquals(0, result.getErrors().size());
        assertEquals(3, result.getOutput().size());
        assertEquals("3 129 1452 src/test/resources/testfile.txt", result.getOutput().get(0));
        assertEquals("3 129 1452 src/test/resources/testfile.txt", result.getOutput().get(1));
        assertEquals("6 258 2904 total", result.getOutput().get(2));
    }

    @Test
    void execute_NoFiles_NoOutputErrorMessage() {
        var wc = new Wc(informant);

        var result = wc.execute();
        assertEquals(0, result.getOutput().size());
        assertEquals(1, result.getErrors().size());
    }

    @Test
    void execute_ConnectionNoFiles_CorrectOutputNoErrors() throws IOException {
        var conn = informant.createConnection();
        informant.send(conn, "322".getBytes());
        var wc = new Wc(informant);
        wc.addConnection(conn);

        var result = wc.execute();
        assertEquals(1, result.getOutput().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("1 1 3", result.getOutput().get(0));
    }

    @Test
    void execute_ConnectionOneFile_ConnectionIgnored() throws IOException {
        var conn = informant.createConnection();
        informant.send(conn, "Aaaand it's gone".getBytes());
        var wc = new Wc(informant);
        wc.addConnection(conn);
        wc.setArgs(new ArrayList<>(Collections.singletonList(new Literal(testFile))));

        var result = wc.execute();
        assertEquals(0, result.getErrors().size());
        assertEquals(1, result.getOutput().size());
    }
}