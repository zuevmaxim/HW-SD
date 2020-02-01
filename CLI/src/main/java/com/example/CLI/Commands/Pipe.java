package com.example.CLI.Commands;

import com.example.CLI.Environment.Informant;
import com.example.CLI.Environment.Informed;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Класс, описывающий поведение pipe'ов.
 */
public class Pipe implements Operation {

    @NotNull private Informant informant;
    @NotNull private Command from;
    @NotNull private Command to;

    public Pipe(@NotNull Command from, @NotNull Command to, @NotNull Informant informant) {
        this.from = from;
        this.to = to;
        this.informant = informant;
    }

    @Override
    public Result execute() {
        if (to instanceof Informed) {
            var result = from.execute();
            var outputBuilder = new StringBuilder();
            for (var line : result.getOutput()) {
                outputBuilder.append(line);
                outputBuilder.append("\n");
            }
            result.clearOutput();

            var conn = informant.createConnection();
            try {
                informant.send(conn, outputBuilder.toString().getBytes());
            } catch (IOException e) {
                result.addError("Pipe: can't work with connection " + conn);
            }
            ((Informed) to).addConnection(conn);

            result.appendResult(to.execute());
            return result;
        } else {
            var result = from.execute();
            result.clearOutput();

            result.appendResult(to.execute());
            return result;
        }
    }
}
