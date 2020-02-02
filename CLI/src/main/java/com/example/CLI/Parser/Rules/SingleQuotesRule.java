package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.Literal;
import com.example.CLI.Commands.Operation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Правило для литералов, обернутых в одинарные кавычки.
 */
public class SingleQuotesRule implements Rule {
    private static final String regex = "\\s*'[^']*'\\s*";
    @NotNull static private Pattern word;
    @Nullable
    private String literal;

    static {
        word = Pattern.compile("'[^']*'");
    }

    @Override @NotNull
    public Type getType() {
        return Type.WORD;
    }

    @Override @NotNull
    public Integer getLevel() {
        return 0;
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
        matcher.find();
        literal = input.substring(matcher.start() + 1, matcher.end() - 1);

        return new ArrayList<>();
    }

    @NotNull
    @Override
    public Operation createOperation(ArrayList<Operation> args) {
        if (literal == null) {
            throw new IllegalStateException("Can't create Operation.");
        }

        return new Literal(literal);
    }
}
