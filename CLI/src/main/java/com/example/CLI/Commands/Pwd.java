package com.example.CLI.Commands;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class Pwd implements Command {
    @Override
    public Result execute() {
        var dir = System.getProperty("user.dir");
        return new Result(new ArrayList<>(Collections.singletonList(dir)), new ArrayList<>());
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        // Useless for this particular command.
    }
}
