package com.example.CLI.Commands;

import com.example.CLI.Environment.Context;
import com.example.CLI.Environment.Informant;
import com.example.CLI.Environment.Informed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Класс-обертка для любой команды. При вызове метода {@link Undefined#execute()} ищет соответствующую команду в контексте.
 */
public class Undefined implements Command, Informed {

    @NotNull private Operation name;
    @NotNull private Context context;
    @NotNull Informant informant;
    @NotNull private ArrayList<Operation> args;
    @NotNull private ArrayList<String> connections;

    public Undefined(@NotNull Context context, @NotNull Operation name, @NotNull Informant informant) {
        this.name = name;
        args = new ArrayList<>();
        this.context = context;
        this.informant = informant;
        connections = new ArrayList<>();
    }

    @Override
    public Result execute() {
        var result = name.execute();
        if (result.getOutput().size() == 0) {
            return new Result(new ArrayList<>(), result.getErrors());
        } else {
            var command = context.getCommand(result.getOutput().get(0));
            command.setArgs(args);
            if (command instanceof Informed) {
                for (var conn: connections) {
                    ((Informed) command).addConnection(conn);
                }
            }
            result.clearOutput();
            result.appendResult(command.execute());

            return result;
        }
    }

    @Override
    public void setArgs(@NotNull ArrayList<Operation> args) {
        this.args = args;
    }

    @Override
    public void addConnection(@NotNull String name) {
        connections.add(name);
    }
}
