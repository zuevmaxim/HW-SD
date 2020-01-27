package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.Contextual;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;


public class Undefined implements Command, Contextual {

    @NotNull private Operation name;
    @Nullable private Context context;
    @NotNull private ArrayList<Operation> args;

    public Undefined(@NotNull Operation name) {
        this.name = name;
        args = new ArrayList<>();
        context = null;
    }

    @Override
    public Result execute() {
        if (context == null) {
            throw new IllegalStateException("Context should be defined.");
        }

        var result = name.execute();
        if (result.getOutput().size() == 0) {
            return new Result(new ArrayList<>(), result.getErrors());
        } else {
            var command = context.getCommand(result.getOutput().get(0));
            command.setArgs(args);
            result.clearOutput();
            result.appendResult(command.execute());

            return result;
        }
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        this.args = args;
    }

    @Override
    public void setContext(@NotNull Context context) {
        this.context = context;
    }
}
