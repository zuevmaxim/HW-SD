package com.example.CLI.Commands;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Класс-оболочка для литералов с правилом full quoting.
 */
public class Literal implements Operation {

    @NotNull private String string;

    public Literal(@NotNull String string) {
        this.string = string;
    }

    @Override
    public Result execute() {
        return new Result(new ArrayList<>(Collections.singletonList(string)), new ArrayList<>());
    }
}
