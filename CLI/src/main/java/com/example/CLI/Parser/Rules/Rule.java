package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.Operation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public interface Rule {

    public enum Type {
        WORD,
        COMMAND,
        SPECIAL
    }

    @NotNull
    Type getType();

    @NotNull
    Integer getLevel();

    boolean isMatching(@NotNull String input);

    @NotNull
    ArrayList<String> split(@NotNull String input);

    @NotNull
    Operation createOperation(ArrayList<Operation> args);
}
