package com.example.CLI.Parser;

import com.example.CLI.Commands.*;
import com.example.CLI.Environment.SimpleContext;
import com.example.CLI.Environment.SimpleInformant;
import com.example.CLI.Parser.Rules.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;


public class ParserBuilder {

    @NotNull
    public static Parser createParser() {
        var commands = new HashMap<String, Supplier<Command>>();
        var informant = new SimpleInformant();
        commands.put("echo", Echo::new);
        commands.put("cat", () -> new Cat(informant));
        commands.put("pwd", Pwd::new);
        commands.put("wc", () -> new Wc(informant));
        var context = new SimpleContext(commands, (name) -> new External(name, informant));

        var rules = new ArrayList<Rule>();
        rules.add(new PipeRule(informant));
        rules.add(new SaveRule(context));
        rules.add(new CommandRule(context, informant));
        rules.add(new ExtractRule(context));
        rules.add(new DoubleQuotesRule(context));
        rules.add(new SingleQuotesRule());
        rules.add(new LiteralRule());

        return new Parser(rules);
    }
}
