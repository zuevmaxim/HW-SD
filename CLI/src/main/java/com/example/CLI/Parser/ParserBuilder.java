package com.example.CLI.Parser;

import com.example.CLI.Commands.Cat;
import com.example.CLI.Commands.Command;
import com.example.CLI.Commands.Echo;
import com.example.CLI.Commands.Pwd;
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
