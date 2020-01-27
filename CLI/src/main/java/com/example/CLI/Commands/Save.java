package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.Contextual;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;


public class Save implements Command, Contextual {

    @NotNull private String variable;
    @NotNull private Operation value;
    @Nullable private Context context;

    public Save(@NotNull String var, @NotNull Operation val) {
        variable = var;
        value = val;
    }

    @Override
    public Result execute() {
        if (context == null) {
            throw new IllegalStateException("Context should be defined.");
        }

        context.setValue(variable, value);

        return new Result();
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        // Useless for this particular command
    }

    @Override
    public void setContext(@NotNull Context context) {
        this.context = context;
    }
}
