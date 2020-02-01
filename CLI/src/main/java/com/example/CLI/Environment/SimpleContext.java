package com.example.CLI.Environment;

import com.example.CLI.Commands.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Контекст выполнения комманд.
 * Содержит в себе переменные окружения и внутренние команды.
 */
public class SimpleContext implements Context {

    @NotNull private HashMap<String, Operation> localVariables;
    @NotNull private HashMap<String, Supplier<Command>> localCommands;
    @NotNull private Function<String, Command> externalCommand;

    public SimpleContext(@NotNull HashMap<String, Supplier<Command>> localCommands, @NotNull Function<String, Command> externalCommand) {
        localVariables = new HashMap<>();
        this.localCommands = localCommands;
        this.externalCommand = externalCommand;
    }

    @Override
    public void setValue(String name, Operation value) {
        localVariables.put(name, value);
    }

    @Override @NotNull
    public Operation getValue(@NotNull String name) {
        if (localVariables.containsKey(name)) {
            return localVariables.get(name);
        } else {
            return new Literal("");
        }
    }

    @Override @NotNull
    public Command getCommand(@NotNull String name) {
        if (localCommands.containsKey(name)) {
            return localCommands.get(name).get();
        } else {
            return externalCommand.apply(name);
        }
    }
}
