package com.example.CLI.Commands;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Класс, описывающий поведение команды 'echo'.
 */
public class Echo implements Command {

    @NotNull private ArrayList<Operation> args;

    public Echo() {
        args = new ArrayList<>();
    }

    @Override
    public Result execute() {
        var output = new StringBuilder();
        var errors = new ArrayList<String>();

        for (var operation: args) {
            var result = operation.execute();
            for (var string: result.getOutput()) {
                output.append(string);
                output.append(" ");
            }
            errors.addAll(result.getErrors());
        }
        var string = output.toString();
        if (string.length() > 0) {
            string = string.substring(0, string.length() - 1);
        }

        return new Result(new ArrayList<>(Collections.singletonList(string)), errors);
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        this.args = args;
    }
}
