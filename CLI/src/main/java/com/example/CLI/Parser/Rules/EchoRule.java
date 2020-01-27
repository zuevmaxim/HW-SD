package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.Echo;
import com.example.CLI.Commands.Operation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EchoRule extends CommandRule {

    private static final String regex = "\\s*echo(\\s+[\\w./+=()*~\\-]+)*\\s*";

    @Override @NotNull
    public Integer getLevel() {
        return 11;
    }

    @Override
    public boolean isMatching(@NotNull String input) {
        return input.matches(regex);
    }

    @Override @NotNull
    public ArrayList<String> split(@NotNull String input) {
        var strings = super.split(input);
        if (strings.get(0).equals("echo")) {
            strings.remove(0);
            return strings;
        } else {
            throw new IllegalStateException("Input does not match with rule for command \'echo\'");
        }
    }

    @Override @NotNull
    public Operation createOperation(ArrayList<Operation> args) {
        var echo = new Echo();
        echo.setArgs(args);
        return echo;
    }
}
