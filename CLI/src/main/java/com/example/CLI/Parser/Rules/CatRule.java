package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.Cat;
import com.example.CLI.Commands.Operation;
import com.example.CLI.Environment.Informant;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class CatRule extends CommandRule {

    private static final String regex = "\\s*cat(\\s+[\\w./+=()*~$\\-]+)*\\s*";
    @NotNull private Informant informant;

    public CatRule(@NotNull Informant informant) {
        this.informant = informant;
    }

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
        if (strings.get(0).equals("cat")) {
            strings.remove(0);
            return strings;
        } else {
            throw new IllegalStateException("Input does not match with rule for command \'echo\'");
        }
    }

    @Override @NotNull
    public Operation createOperation(ArrayList<Operation> args) {
        var cat = new Cat(informant);
        cat.setArgs(args);
        return cat;
    }
}
