package com.example.CLI.Parser.Rules;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Pattern;


public abstract class CommandRule implements Rule {

    protected static final String regex =
            "\\s*([\\w./+=()*~$\\-]+|'[^']+'|\"(\\\\\"|[^\\\\\"])+\")" +
            "(\\s+([\\w./+=()*~$\\-]+|'[^']+'|\"(\\\\\"|[^\\\\\"])+\"))*\\s*";
    @NotNull static private Pattern word;

    static {
        word = Pattern.compile("[\\w./+=()*~$\\-]+|'[^']+'|\"(\\\\\"|[^\\\\\"])+\"");
    }

    @Override @NotNull
    public Type getType() {
        return Type.COMMAND;
    }

    @Override @NotNull
    public ArrayList<String> split(@NotNull String input) {
        if (!input.matches(regex)) {
            throw new IllegalStateException("Input does not match with rule for commands.");
        }

        var matcher = word.matcher(input);
        var result = new ArrayList<String>();
        while (matcher.find()) {
            result.add(input.substring(matcher.start(), matcher.end()));
        }

        return result;
    }
}
