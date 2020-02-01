package com.example.CLI.Commands;

import com.example.CLI.Environment.Informant;
import com.example.CLI.Environment.Informed;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Класс, описывающий поведение команды 'wc'.
 */
public class Wc implements Command, Informed {

    @NotNull private Informant informant;
    @NotNull private ArrayList<Operation> args;
    @NotNull private ArrayList<String> connections;

    public Wc(@NotNull Informant informant) {
        this.informant = informant;
        args = new ArrayList<>();
        connections = new ArrayList<>();
    }

    @Override
    public Result execute() {
        if (args.size() > 0) {
            int totalLines = 0;
            int totalWords = 0;
            int totalBytes = 0;

            var output = new ArrayList<String>();
            var errors = new ArrayList<String>();

            for (var arg: args) {
                var result = arg.execute();
                errors.addAll(result.getErrors());

                for (var file: result.getOutput()) {
                    try {
                        var bytes = informant.getAndClose(file);
                        var scanner = new Scanner(new ByteArrayInputStream(bytes));

                        int lines = 0;
                        int words = 0;
                        while (scanner.hasNext()) {
                            lines++;
                            words += scanner.nextLine().split("\\s").length;
                        }
                        totalBytes += bytes.length;
                        totalLines += lines;
                        totalWords += words;

                        output.add(lines + " " + words + " " + bytes.length + " " + file);
                    } catch (IOException e) {
                        errors.add("Can't read file \'" + file + "\'.");
                    }
                }
            }
            if (output.size() == 1) {
                output.remove(0);
                output.add(totalLines + " " + totalWords + " " + totalBytes);
            } else if (output.size() == 0) {
                output.add(totalLines + " " + totalWords + " " + totalBytes);
            } else {
                output.add(totalLines + " " + totalWords + " " + totalBytes + " total");
            }

            return new Result(output, errors);
        } else if (connections.size() > 0) {
            int totalLines = 0;
            int totalWords = 0;
            int totalBytes = 0;

            var output = new ArrayList<String>();
            var errors = new ArrayList<String>();

            for (var conn: connections) {
                try {
                    var bytes = informant.getAndClose(conn);
                    var scanner = new Scanner(new ByteArrayInputStream(bytes));

                    while (scanner.hasNext()) {
                        totalLines++;
                        totalWords += scanner.nextLine().split("\\s").length;
                    }
                    totalBytes += bytes.length;
                } catch (IOException e) {
                    errors.add("wc: can't work with connection " + conn);
                }

                output.add(totalLines + " " + totalWords + " " + totalBytes);
            }
            return new Result(output, errors);
        } else {
            return new Result(new ArrayList<>(), new ArrayList<>(Collections.singletonList("Usage \'wc\': wc [FILE]...")));
        }
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        this.args = args;
    }

    @Override
    public void addConnection(@NotNull String name) {
        connections.add(name);
    }
}
