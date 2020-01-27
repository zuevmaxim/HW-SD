package com.example.CLI.Environment;

import com.example.CLI.Commands.Command;
import com.example.CLI.Commands.Operation;
import org.jetbrains.annotations.NotNull;


public interface Context {

    void setValue(String name, Operation value);

    @NotNull
    Operation getValue(@NotNull String name);

    @NotNull
    Command getCommand(@NotNull String name);
}
