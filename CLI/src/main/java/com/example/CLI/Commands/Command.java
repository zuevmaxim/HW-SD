package com.example.CLI.Commands;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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
}
