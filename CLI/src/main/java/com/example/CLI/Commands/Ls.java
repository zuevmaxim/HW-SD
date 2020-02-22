package com.example.CLI.Commands;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'ls' выводит содержимое директории, переданной в параметре или, если нет параметров, текущей директории.
 */
public class Ls implements Command {
    @NotNull
    private List<Operation> args = Collections.emptyList();

    @Override
    public Result execute() {
        final var output = new ArrayList<String>();
        final var errors = new ArrayList<String>();
        var directory = "";
        var stringArgs = executeArgs(args, errors);
        if (stringArgs.size() == 0) {
            directory = Pwd.currentDirectory();
        } else if (stringArgs.size() == 1) {
            directory = stringArgs.get(0);
        } else {
            return illegalArgumentsResult(errors);
        }
        try {
            final var contents = listDirectoryContents(directory);
            output.addAll(contents);
        } catch (IOException e) {
            errors.add("Error while searching in directory \'" + directory + "\'");
        }
        return new Result(output, errors);
    }

    @NotNull
    private static Result illegalArgumentsResult(@NotNull ArrayList<String> errors) {
        errors.add("Usage \'ls\': ls [DIR]");
        return new Result(new ArrayList<>(), errors);
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        this.args = args;
    }

    /**
     * Список имен файлов в директории.
     *
     * @param directory директория для поиска
     * @throws IOException если переданный путь к директории невалидный
     */
    public static List<String> listDirectoryContents(@NotNull String directory) throws IOException {
        return Files.list(new File(directory).toPath())
                .map(path -> path.getFileName().toString())
                .collect(Collectors.toList());
    }
}
