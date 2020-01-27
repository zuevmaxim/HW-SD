package com.example.CLI.Commands;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;


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

        return new Result(new ArrayList<>(Collections.singletonList(output.toString())), errors);
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        this.args = args;
    }
}
