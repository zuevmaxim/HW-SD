package com.example.CLI.Commands;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public interface Command extends Operation {

    @Override
    Result execute();

    void setArgs(@NotNull ArrayList<Operation> args);
}
