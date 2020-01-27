package com.example.CLI;

import com.example.CLI.Commands.Result;
import com.example.CLI.Parser.Parser;
import com.example.CLI.Parser.Rules.EchoRule;
import com.example.CLI.Parser.Rules.LiteralRule;
import com.example.CLI.Parser.Rules.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Scanner;

public class CLI {

    static public void main(String[] args) {
        var rules = new ArrayList<Rule>();
        rules.add(new EchoRule());
        rules.add(new LiteralRule());
        var parser = new Parser(rules);

        var scanner = new Scanner(System.in);
        var input = scanner.nextLine();
        printResult(parser.parse(input).execute());
    }

    static private void printResult(@NotNull Result result) {
        for (var line: result.getOutput()) {
            System.out.println(line);
        }
        if (result.getErrors().size() > 0) {
            for (var error: result.getErrors()) {
                System.out.println("\u001B[31m" + error + "\u001B[0m");
            }
        }
    }
}
