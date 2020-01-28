package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.Operation;
import com.example.CLI.Commands.Undefined;
import com.example.CLI.Environment.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class UndefinedRule extends CommandRule {

    @NotNull private Context context;

    public UndefinedRule(@NotNull Context context) {
        this.context = context;
    }

    @Override @NotNull
    public Integer getLevel() {
        return 10;
    }

    @Override
    public boolean isMatching(@NotNull String input) {
        return input.matches(CommandRule.regex);
    }

    @Override @NotNull
    public Operation createOperation(ArrayList<Operation> args) {
        var command = new Undefined(context, args.get(0));
        args.remove(0);
        command.setArgs(args);

        return command;
    }
}
