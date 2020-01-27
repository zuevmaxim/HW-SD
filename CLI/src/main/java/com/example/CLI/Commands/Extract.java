package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.Contextual;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class Extract implements Operation, Contextual {

    @NotNull private String variable;
    @Nullable private Context context;

    public Extract(@NotNull String var) {
        variable = var;
    }

    @Override
    public Result execute() {
        if (context == null) {
            throw new IllegalStateException("Context should be defined.");
        }

        return context.getValue(variable).execute();
    }

    @Override
    public void setContext(@NotNull Context context) {
        this.context = context;
    }
}
