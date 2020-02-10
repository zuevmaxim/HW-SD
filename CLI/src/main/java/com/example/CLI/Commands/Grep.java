package com.example.CLI.Commands;

import com.example.CLI.Environment.Informant;
import com.example.CLI.Environment.Informed;
import io.airlift.airline.Arguments;
import io.airlift.airline.Option;
import io.airlift.airline.SingleCommand;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Grep implements Command, Informed {

    @NotNull private Informant informant;
    @NotNull private ArrayList<Operation> args;
    @NotNull private ArrayList<String> connections;


    public Grep(@NotNull Informant informant) {
        this.informant = informant;
        args = new ArrayList<>();
        connections = new ArrayList<>();
    }

    @Override
    public Result execute() {
        var result = new Result();
        var options = getOptions(result);

        if (options == null || options.options == null) {
            result.addError("Usage \'grep\': grep [ARG]... PATTERN [FILE]...");
        } else if (options.options.size() > 1) {
            var regex = options.options.get(0);
            if (options.onlyWords) {
                regex = "\\b" + regex + "\\b";
            }
            Pattern pattern;
            if (options.caseIgnored) {
                pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            } else {
                pattern = Pattern.compile(regex);
            }

            options.options.remove(0);
            for (var file: options.options) {
                try {
                    int lines = 0;
                    var scanner = new Scanner(new ByteArrayInputStream(informant.getAndClose(file)));

                    while (scanner.hasNext()) {
                        var string = scanner.nextLine();

                        if (pattern.matcher(string).find()) {
                            result.addOutputLine(string);
                            lines = options.lines - 1;
                        } else if (lines > 0) {
                            result.addOutputLine(string);
                            lines--;
                        }
                    }
                } catch (IOException e) {
                    result.addError("Can't read file \'" + file + "\'.");
                }
            }
        } else if (options.options.size() == 1 && connections.size() > 0) {
            var regex = options.options.get(0);
            if (options.onlyWords) {
                regex = "\\b" + regex + "\\b";
            }
            Pattern pattern;
            if (options.caseIgnored) {
                pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            } else {
                pattern = Pattern.compile(regex);
            }

            for (var conn: connections) {
                try {
                    int lines = options.lines - 1;
                    var scanner = new Scanner(new ByteArrayInputStream(informant.getAndClose(conn)));

                    while (scanner.hasNext()) {
                        var string = scanner.nextLine();

                        if (pattern.matcher(string).find()) {
                            result.addOutputLine(string);
                            lines = options.lines - 1;
                        } else if (lines > 0) {
                            result.addOutputLine(string);
                            lines--;
                        }
                    }
                } catch (IOException e) {
                    result.addError("grep: Can't work with connection " + conn);
                }
            }
        } else {
            result.addError("Usage \'grep\': grep [ARG]... PATTERN [FILE]...");
        }

        return result;
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        this.args = args;
    }

    @Override
    public void addConnection(@NotNull String name) {
        connections.add(name);
    }

    private Options getOptions(@NotNull Result result) {
        var strings = new String[args.size()];
        for (int i = 0; i < args.size(); i++) {
            var res = args.get(i).execute();
            result.addErrors(res.getErrors());
            strings[i] = res.getOutput().get(0);
        }

        return SingleCommand.singleCommand(Options.class).parse(strings);
    }

    @io.airlift.airline.Command(name = "grep", description = "print lines matching a pattern")
    public static class Options {

        @Arguments()
        public ArrayList<String> options;

        @Option(name = "-i", description = "ignore case distinctions")
        public boolean caseIgnored = false;

        @Option(name = "-w", description = "match only whole words")
        public boolean onlyWords = false;

        @Option(name = "-A", description = "print n lines after matching lines")
        public int lines = 1;
    }
}
