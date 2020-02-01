package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.Operation;
import com.example.CLI.Commands.Undefined;
import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.Informant;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Правило для комманд.
 */
public class CommandRule implements Rule {

    @NotNull private Context context;
    @NotNull private Informant informant;
    protected static final String regex =
            "\\s*([\\w./+=()*~$\\-]+|'[^']+'|\"(\\\\\"|[^\\\\\"])+\")" +
            "(\\s+([\\w./+=()*~$\\-]+|'[^']+'|\"(\\\\\"|[^\\\\\"])+\"))*\\s*";
    @NotNull static private Pattern word;

    static {
        word = Pattern.compile("[\\w./+=()*~$\\-]+|'[^']+'|\"(\\\\\"|[^\\\\\"])+\"");
    }

    public CommandRule(@NotNull Context context, @NotNull Informant informant) {
        this.context = context;
        this.informant = informant;
    }

    @Override @NotNull
    public Type getType() {
        return Type.COMMAND;
    }

    @NotNull
    @Override
    public Integer getLevel() {
        return 10;
    }

    @Override
    public boolean isMatching(@NotNull String input) {
        return input.matches(regex);
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

    @NotNull
    @Override
    public Operation createOperation(ArrayList<Operation> args) {
        var command = new Undefined(context, args.get(0), informant);
        args.remove(0);
        command.setArgs(args);

        return command;
    }
}
