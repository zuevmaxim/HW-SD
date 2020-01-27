package com.example.CLI.Environment;

import com.example.CLI.Commands.Command;
import com.example.CLI.Commands.External;
import com.example.CLI.Commands.Literal;
import com.example.CLI.Commands.Operation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


public class SimpleContext implements Context {

    @NotNull private HashMap<String, Operation> localVariables;
    @NotNull private HashMap<String, String> localCommands;

    public SimpleContext(@NotNull HashMap<String, String> localCommands) {
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
            try {
                var commandClass = Class.forName(localCommands.get(name));
                return (Command) commandClass.newInstance();
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Can't find class " + localCommands.get(name) + ", but its name was found in the context.");
            } catch (IllegalAccessException | InstantiationException e) {
                throw new IllegalStateException("Can't create object of " + localCommands.get(name) + " class (check, that default constructor exists and has public access).");
            }
        } else {
            return new External(name);
        }
    }
}
