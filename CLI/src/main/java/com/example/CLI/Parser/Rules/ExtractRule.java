package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.Extract;
import com.example.CLI.Commands.Operation;
import com.example.CLI.Environment.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class ExtractRule implements Rule {

    @NotNull private Context context;
    private static final String regex = "\\s*\\$[\\w./+()*~\\-]+\\s*";
    @Nullable private String name;
    @NotNull static private Pattern word;

    static {
        word = Pattern.compile("[\\w./+()*~\\-]+");
    }

    public ExtractRule(@NotNull Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public Type getType() {
        return Type.WORD;
    }

    @NotNull
    @Override
    public Integer getLevel() {
        return 1;
    }

    @Override
    public boolean isMatching(@NotNull String input) {
        return input.matches(regex);
    }

    @NotNull
    @Override
    public ArrayList<String> split(@NotNull String input) {
        if (!input.matches(regex)) {
            throw new IllegalStateException("Input does not match with rule for commands.");
        }

        var matcher = word.matcher(input);
        matcher.find();
        name = input.substring(matcher.start(), matcher.end());

        return new ArrayList<>();
    }

    @NotNull
    @Override
    public Operation createOperation(ArrayList<Operation> args) {
        if (name == null) {
            throw new IllegalStateException("split() method should be called before splitting.");
        }

        return new Extract(context, name);
    }
}
