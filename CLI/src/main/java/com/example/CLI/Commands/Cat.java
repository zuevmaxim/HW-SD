package com.example.CLI.Commands;

import com.example.CLI.Environment.Informant;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Cat implements Command {

    @NotNull private ArrayList<Operation> args;
    @NotNull private Informant informant;

    public Cat(@NotNull Informant informant) {
        args = new ArrayList<>();
        this.informant = informant;
    }

    @Override
    public Result execute() {
        if (args.size() == 0) {
            return new Result(new ArrayList<>(), new ArrayList<>(Collections.singletonList("Usage \'cat\': cat [FILE]...")));
        } else {
            var output = new ArrayList<String>();
            var errors = new ArrayList<String>();
            for (var arg: args) {
                var result = arg.execute();
                errors.addAll(result.getErrors());
                for (var file: result.getOutput()) {
                    try {
                        var scanner = new Scanner(new ByteArrayInputStream(informant.getAndClose(file)));
                        while (scanner.hasNext()) {
                            output.add(scanner.nextLine());
                        }
                    } catch (IOException e) {
                        errors.add("Can't read file \'" + file + "\'.");
                    }
                }
            }
            return new Result(output, errors);
        }
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        this.args = args;
    }
}
