package com.example.CLI;

import com.example.CLI.Commands.Result;
import com.example.CLI.Parser.Parser;
import com.example.CLI.Parser.ParserBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;
import java.util.function.Supplier;


public class CLI {

    static public void main(String[] args) {
        run(ParserBuilder::createParser);
    }

    static public void run(@NotNull Supplier<Parser> parserSupplier) {
        var scanner = new Scanner(System.in);
        var parser = parserSupplier.get();

        while (scanner.hasNext()) {
            var input = scanner.nextLine();
            if (input.matches("\\s*exit\\s*")) {
                return;
            } else {
                printResult(parser.parse(input).execute());
            }
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
}
