package com.example.CLI.Commands;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Класс, описывающий поведение команды pwd.
 */
public class Pwd implements Command {
    @Override
    public Result execute() {
        var dir = currentDirectory();
        return new Result(new ArrayList<>(Collections.singletonList(dir)), new ArrayList<>());
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        // Useless for this particular command.
    }

    public static String currentDirectory() {
        return System.getProperty("user.dir");
    }
}
