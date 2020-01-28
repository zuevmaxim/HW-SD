package com.example.CLI;

import com.example.CLI.Commands.*;
import com.example.CLI.Environment.SimpleContext;
import com.example.CLI.Environment.SimpleInformant;
import com.example.CLI.Parser.Parser;
import com.example.CLI.Parser.Rules.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Supplier;

public class CLI {

    static public void main(String[] args) {
        var parser = createParser();
        var scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            var input = scanner.nextLine();
            printResult(parser.parse(input).execute());
        }
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

    static private Parser createParser() {
        var commands = new HashMap<String, Supplier<Command>>();
        var context = new SimpleContext(commands);
        var informant = new SimpleInformant();
        commands.put("echo", Echo::new);
        commands.put("cat", () -> new Cat(informant));
        commands.put("pwd", Pwd::new);

        var rules = new ArrayList<Rule>();
        rules.add(new SaveRule(context));
        rules.add(new EchoRule());
        rules.add(new CatRule(informant));
        rules.add(new PwdRule());
        rules.add(new UndefinedRule(context));
        rules.add(new ExtractRule(context));
        rules.add(new LiteralRule());

        return new Parser(rules);
    }
}
