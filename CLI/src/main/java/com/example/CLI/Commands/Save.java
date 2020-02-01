package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import org.jetbrains.annotations.NotNull;

/**
 * Класс, описывающий поведение оператора '='.
 */
public class Save implements Operation {

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
}
