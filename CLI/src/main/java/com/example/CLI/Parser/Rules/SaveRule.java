package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.Operation;
import com.example.CLI.Commands.Save;
import com.example.CLI.Environment.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class SaveRule implements Rule {

    private static final String regex = "\\s*[\\w./+()*~\\-]+\\s*=\\s*[\\w./+()*~\\-]+\\s*";
    @Nullable private String name;
    @NotNull private Context context;
    @NotNull static private Pattern word;

    static {
        word = Pattern.compile("[\\w./+()*~\\-]+");
    }

    public SaveRule(@NotNull Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public Type getType() {
        return Type.COMMAND;
    }

    @NotNull
    @Override
    public Integer getLevel() {
        return 99;
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
        var result = new ArrayList<String>();
        while (matcher.find()) {
            result.add(input.substring(matcher.start(), matcher.end()));
        }
        name = result.get(0);
        result.remove(0);

        return result;
    }

    @NotNull
    @Override
    public Operation createOperation(ArrayList<Operation> args) {
        if (name == null) {
            throw new IllegalStateException("split() method should be called before splitting.");
        }

        var command = new Save(name, args.get(0));
        command.setContext(context);
        return command;
    }
}
