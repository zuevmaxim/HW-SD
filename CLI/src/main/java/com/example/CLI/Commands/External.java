package com.example.CLI.Commands;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class External implements Command {

    @NotNull private String name;
    @NotNull private ArrayList<Operation> args;

    public External(@NotNull String fullName) {
        name = fullName;
        args = new ArrayList<>();
    }

    @Override
    public Result execute() {
        var command = new ArrayList<String>();
        var errors = new ArrayList<String>();
        command.add(name);
        for (var operation: args) {
            var result = operation.execute();
            if (result.getOutput().size() > 0) {
                command.add(result.getOutput().get(0));
            }
            errors.addAll(result.getErrors());
        }

        var result = new Result();
        result.addErrors(errors);
        try {
            var process = Runtime.getRuntime().exec(command.toArray(new String[0]));
            var stdout = new Scanner(process.getInputStream());
            var stderr = new Scanner(process.getErrorStream());

            while (stdout.hasNext()) {
                result.addOutputLine(stdout.nextLine());
            }
            while (stderr.hasNext()) {
                result.addError(stderr.nextLine());
            }

            return result;
        } catch (IOException e) {
            errors.add("Can't run external program \'" + name + "\'.");

            return new Result(new ArrayList<>(), errors);
        }
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        this.args = args;
    }
}
