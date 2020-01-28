package com.example.CLI.Commands;

import com.example.CLI.Environment.Informant;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Wc implements Command {

    @NotNull private Informant informant;
    @NotNull private ArrayList<Operation> args;

    public Wc(@NotNull Informant informant) {
        this.informant = informant;
        args = new ArrayList<>();
    }

    @Override
    public Result execute() {
        if (args.size() == 0) {
            return new Result(new ArrayList<>(), new ArrayList<>(Collections.singletonList("Usage \'wc\': wc [FILE]...")));
        } else {
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
            } else {
                output.add(totalLines + " " + totalWords + " " + totalBytes + " total");
            }

            return new Result(output, errors);
        }
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        this.args = args;
    }
}
