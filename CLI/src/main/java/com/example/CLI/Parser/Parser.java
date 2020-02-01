package com.example.CLI.Parser;

import com.example.CLI.Commands.Operation;
import com.example.CLI.Commands.Result;
import com.example.CLI.Parser.Rules.Rule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Парсер, распознающий ввод.
 */
public class Parser {

    @NotNull private ArrayList<Rule> rules;

    /**
     * Конструктор.
     * @param rules - список правил, которыми будет пользоваться парсер при разборе ввода
     */
    public Parser(@NotNull ArrayList<Rule> rules) {
        rules.sort(Comparator.comparing(Rule::getLevel));
        this.rules = rules;
    }

    /**
     * Строит дерево разбора.
     * @param input - строка ввода
     * @return корень построенного дерева
     */
    @NotNull
    public Operation parse(@NotNull String input) {
        return parse(input, 100, null);
    }

    /**
     * Строит дерево разбора.
     * @param input - строка ввода
     * @param level - максимальный уровень правил для разбора
     * @param targetType - тип правил для разбора (если null, то использует все правила)
     * @return корень построенного дерева
     */
    @NotNull
    private Operation parse(@NotNull String input, int level, @Nullable Rule.Type targetType) {
        int start = rules.size() - 1;
        for (var rule: rules) {
            if (rule.getLevel() > level) {
                start--;
            } else {
                break;
            }
        }

        for (int i = start; i >= 0; i--) {
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
                        for (var string: parts) {
                            args.add(parse(string, i - 1, Rule.Type.WORD));
                        }
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
