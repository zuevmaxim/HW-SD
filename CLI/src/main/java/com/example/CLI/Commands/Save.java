package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class Save implements Command {

    @NotNull private String variable;
    @NotNull private Operation value;
    @NotNull private Context context;

    public Save(@NotNull Context context, @NotNull String var, @NotNull Operation val) {
        this.context = context;
        variable = var;
        value = val;
    }

    @Override
    public Result execute() {
        context.setValue(variable, value);

        return new Result();
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        // Useless for this particular command
    }
}
