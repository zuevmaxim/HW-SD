package com.example.CLI.Commands;

import com.example.CLI.Environment.Informant;
import com.example.CLI.Environment.Informed;
import com.example.CLI.PathUtils;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Класс, описывающий поведение команды 'cat'.
 */
public class Cat implements Command, Informed {

    @NotNull private ArrayList<Operation> args;
    @NotNull private Informant informant;
    @NotNull private ArrayList<String> connections;

    public Cat(@NotNull Informant informant) {
        args = new ArrayList<>();
        this.informant = informant;
        connections = new ArrayList<>();
    }

    @Override
    public Result execute() {
        if (args.size() > 0) {
            var output = new ArrayList<String>();
            var errors = new ArrayList<String>();

            for (var arg: args) {
                var result = arg.execute();
                errors.addAll(result.getErrors());
                for (var file: result.getOutput()) {
                    try {
                        var name = PathUtils.pathToAbsolute(file);
                        var scanner = new Scanner(new ByteArrayInputStream(informant.getAndClose(name)));
                        while (scanner.hasNext()) {
                            output.add(scanner.nextLine());
                        }
                    } catch (IOException e) {
                        errors.add("Can't read file \'" + file + "\'.");
                    }
                }
            }

            return new Result(output, errors);
        } else if (connections.size() > 0) {
            var output = new ArrayList<String>();
            var errors = new ArrayList<String>();

            for (var conn: connections) {
                try {
                    var scanner = new Scanner(new ByteArrayInputStream(informant.getAndClose(conn)));
                    while (scanner.hasNext()) {
                        output.add(scanner.nextLine());
                    }
                } catch (IOException e) {
                    errors.add("cat: Can't work with connection " + conn);
                }
            }

            return new Result(output, errors);
        } else {
            return new Result(new ArrayList<>(), new ArrayList<>(Collections.singletonList("Usage \'cat\': cat [FILE]...")));
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
