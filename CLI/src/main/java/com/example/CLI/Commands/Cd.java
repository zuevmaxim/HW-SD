package com.example.CLI.Commands;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Команда 'cd' меняет текущую директорию на указанную.
 * Если нет параметров, переходит в домашнюю директорию.
 */
public class Cd implements Command {
    @NotNull
    private List<Operation> args = Collections.emptyList();

    /**
     * Получает домашнюю директорию.
     */
    public static String getHomeDirectory() {
        return System.getProperty("user.home");
    }

    /**
     * Изменяет текущую директорию.
     */
    public static void changeCurrentDirectory(String directory) throws IOException {
        final var dir = new File(directory);
        var changeToDirectory = "";
        if (dir.isAbsolute()) {
            changeToDirectory = dir.getCanonicalPath();
        } else {
            final var parent = Pwd.currentDirectory();
            changeToDirectory = new File(parent, directory).getCanonicalPath();
        }
        if (!new File(changeToDirectory).exists()) {
            throw new IOException("No such file or directory: " + changeToDirectory);
        }
        System.setProperty("user.dir", changeToDirectory);
    }

    @NotNull
    private static Result illegalArgumentsResult(@NotNull ArrayList<String> errors) {
        errors.add("Usage \'cd\': cd [DIR]");
        return new Result(new ArrayList<>(), errors);
    }

    @Override
    public Result execute() {
        final var errors = new ArrayList<String>();
        var directory = "";
        final var stringArgs = executeArgs(args, errors);
        if (stringArgs.size() == 0) {
            directory = getHomeDirectory();
        } else if (stringArgs.size() == 1) {
            directory = stringArgs.get(0);
        } else {
            return illegalArgumentsResult(errors);
        }

        try {
            changeCurrentDirectory(directory);
        } catch (IOException e) {
            errors.add("Error while changing to directory \'" + directory + "\': " + e.getMessage());
        }

        return new Result(new ArrayList<>(), errors);
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        this.args = args;
    }
}
