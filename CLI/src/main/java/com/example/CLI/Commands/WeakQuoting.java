package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class WeakQuoting implements Operation {

    @NotNull private String string;
    @NotNull private Context context;

    public WeakQuoting(@NotNull Context context, @NotNull String string) {
        this.string = string;
        this.context = context;
    }

    @Override
    public Result execute() {
        string = string.replaceAll("\\\\\"" ,"\"");
        var word = Pattern.compile("\\$[\\w./+()*~\\-]+");
        var result = new Result();

        while (true) {
            var matcher = word.matcher(string);
            if (matcher.find()) {
                var res = context.getValue(string.substring(matcher.start() + 1, matcher.end())).execute();
                result.addErrors(res.getErrors());
                string = string.substring(0, matcher.start()) + res.getOutput().get(0) + string.substring(matcher.end());
            } else {
                break;
            }
        }

        result.addOutputLine(string);

        return result;
    }
}
