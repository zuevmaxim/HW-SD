package com.example.CLI.Environment;

import com.example.CLI.Commands.Command;
import com.example.CLI.Commands.External;
import com.example.CLI.Commands.Literal;
import com.example.CLI.Commands.Operation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Supplier;


public class SimpleContext implements Context {

    @NotNull private HashMap<String, Operation> localVariables;
    @NotNull private HashMap<String, Supplier<Command>> localCommands;

    public SimpleContext(@NotNull HashMap<String, Supplier<Command>> localCommands) {
        localVariables = new HashMap<>();
        this.localCommands = localCommands;
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
            return new External(name);
        }
    }
}
