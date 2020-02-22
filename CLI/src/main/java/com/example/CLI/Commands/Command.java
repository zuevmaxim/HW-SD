package com.example.CLI.Commands;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Каждая команда наследуется от этого интерфейса.
 */
public interface Command extends Operation {

    @Override
    Result execute();

    /**
     * Определяет аргументы команды.
     * @param args - объекты классов, реализующих интерфейс Operation
     */
    void setArgs(@NotNull ArrayList<Operation> args);

    /**
     * Вычисляет аргументы команды.
     *
     * @param args   список аргументов команды
     * @param errors список, куда складываются ошибки при выполнении
     * @return список результатов
     */
    default List<String> executeArgs(@NotNull List<Operation> args, @NotNull List<String> errors) {
        final var result = new ArrayList<String>();
        for (var arg : args) {
            final var argResult = arg.execute();
            errors.addAll(argResult.getErrors());
            result.addAll(argResult.getOutput());
        }
        return result;
    }
}
