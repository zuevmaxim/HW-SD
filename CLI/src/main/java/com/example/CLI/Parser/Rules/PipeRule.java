package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.Command;
import com.example.CLI.Commands.Operation;
import com.example.CLI.Commands.Pipe;
import com.example.CLI.Environment.Informant;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class PipeRule implements Rule {

    protected static final String regex =
            "(\\s*[^|\\s]+(\\s+[^|\\s]+)*\\s*\\|)+" +
            "(\\s*[^|\\s]+(\\s+[^|\\s]+)*\\s*)";
    @NotNull static private Pattern leftArg;
    @NotNull static private Pattern leftPart;
    @NotNull private Informant informant;

    static {
        leftArg = Pattern.compile(
                "(\\s*[^|\\s]+(\\s+[^|\\s]+)*\\s*\\|)*" +
                "(\\s*[^|\\s]+(\\s+[^|\\s]+)*\\s*)"
        );
        leftPart = Pattern.compile("(\\s*[^|\\s]+(\\s+[^|\\s]+)*\\s*\\|)+");
    }

    public PipeRule(@NotNull Informant informant) {
        this.informant = informant;
    }

    @NotNull
    @Override
    public Type getType() {
        return Type.SPECIAL;
    }

    @NotNull
    @Override
    public Integer getLevel() {
        return 100;
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

        var result = new ArrayList<String>();

        var matcher = leftPart.matcher(input);
        matcher.find();
        var left = input.substring(matcher.start(), matcher.end());
        var right = input.substring(matcher.end());

        matcher = leftArg.matcher(left);
        matcher.find();
        result.add(left.substring(matcher.start(), matcher.end()));
        result.add(right);

        return result;
    }

    @NotNull
    @Override
    public Operation createOperation(ArrayList<Operation> args) {
        return new Pipe((Command) args.get(0), (Command) args.get(1), informant);
    }
}
