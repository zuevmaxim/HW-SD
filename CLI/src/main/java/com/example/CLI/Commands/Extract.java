package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import org.jetbrains.annotations.NotNull;

/**
 * Класс, описывающий поведение операции '$'.
 */
public class Extract implements Operation {

    @NotNull private String variable;
    @NotNull private Context context;

    public Extract(@NotNull Context context, @NotNull String var) {
        this.context = context;
        variable = var;
    }

    @Override
    public Result execute() {
        return context.getValue(variable).execute();
    }
}
