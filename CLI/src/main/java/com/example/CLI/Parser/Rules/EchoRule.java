package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.Echo;
import com.example.CLI.Commands.Operation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class EchoRule implements Rule {

    private static final String regex = "\\s*echo(\\s+\\w+)*\\s*";
    @NotNull static private Pattern word;

    static {
        word = Pattern.compile("\\w+");
    }

    @Override @NotNull
    public Type getType() {
        return Type.COMMAND;
    }

    @Override @NotNull
    public Integer getLevel() {
        return 11;
    }

    @Override
    public boolean isMatching(@NotNull String input) {
        return input.matches(regex);
    }

    @NotNull
    @Override
    public ArrayList<String> split(@NotNull String input) {
        if (!input.matches(regex)) {
            throw new IllegalStateException("Input does not match with rule.");
        }

        var matcher = word.matcher(input);
        var result = new ArrayList<String>();
        matcher.find();  // skip command name
        while (matcher.find()) {
            result.add(input.substring(matcher.start(), matcher.end()));
        }

        return result;
    }

    @NotNull
    @Override
    public Operation createOperation(ArrayList<Operation> args) {
        var echo = new Echo();
        echo.setArgs(args);
        return echo;
    }
}
