package com.example.CLI.Parser;

import com.example.CLI.Commands.Operation;
import com.example.CLI.Commands.Result;
import com.example.CLI.Parser.Rules.Rule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Parser {

    @NotNull private ArrayList<Rule> rules;

    public Parser(@NotNull ArrayList<Rule> rules) {
        rules.sort(Comparator.comparing(Rule::getLevel));
        this.rules = rules;
    }

    @NotNull
    public Operation parse(@NotNull String input) {
        return parse(input, rules.size() - 1, null);
    }

    @NotNull
    private Operation parse(@NotNull String input, int level, @Nullable Rule.Type targetType) {
        for (int i = level; i >= 0; i--) {
            var rule = rules.get(i);
            if (targetType != null && rule.getType() != targetType) {
                continue;
            } else if (rule.isMatching(input)) {
                var parts = rule.split(input);
                var args = new ArrayList<Operation>();
                switch (rule.getType()) {
                    case SPECIAL: {
                        for (var string: parts) {
                            args.add(parse(string, i, null));
                        }
                        break;
                    }
                    case COMMAND: {
                        for (var string: parts) {
                            args.add(parse(string, i - 1, Rule.Type.WORD));
                        }
                        break;
                    }
                    case WORD: {
                        break;
                    }
                }

                return rule.createOperation(args);
            }
        }

        return new Operation() {
            @Override
            public Result execute() {
                return new Result(new ArrayList<>(), new ArrayList<>(Collections.singletonList("Can't parse input.")));
            }
        };
    }
}
