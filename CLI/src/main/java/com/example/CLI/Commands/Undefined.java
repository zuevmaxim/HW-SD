package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;


public class Undefined implements Command {

    @NotNull private Operation name;
    @NotNull private Context context;
    @NotNull private ArrayList<Operation> args;

    public Undefined(@NotNull Context context, @NotNull Operation name) {
        this.name = name;
        args = new ArrayList<>();
        this.context = context;
    }

    @Override
    public Result execute() {
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
}
