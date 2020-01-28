package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.Operation;
import com.example.CLI.Commands.Pwd;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PwdRule extends CommandRule {

    protected static final String regex = "\\s*pwd\\s*";

    @NotNull
    @Override
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
        if (strings.size() == 1 && strings.get(0).equals("pwd")) {
            strings.remove(0);
            return strings;
        } else {
            throw new IllegalStateException("Input does not match with rule for command \'pwd\'");
        }
    }

    @NotNull
    @Override
    public Operation createOperation(ArrayList<Operation> args) {
        return new Pwd();
    }
}
