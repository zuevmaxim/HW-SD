package com.example.CLI.Commands;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Класс-оболочка для результатов выполнения всех объектов в языке. Содержит строчки вывода и ошибок выполненной команды.
 */
public class Result {

     @NotNull private ArrayList<String> out;
     @NotNull private ArrayList<String> err;

     public Result() {
         out = new ArrayList<>();
         err = new ArrayList<>();
     }

     public Result(@NotNull ArrayList<String> out, @NotNull ArrayList<String> errors) {
         this.out = out;
         err = errors;
     }

     public void addOutputLine(@NotNull String line) {
        out.add(line);
     }

     public void addError(@NotNull String error) {
         err.add(error);
     }

     public void addErrors(@NotNull ArrayList<String> errors) {
         err.addAll(errors);
     }

     public void clearOutput() {
         out.clear();
     }

     public void appendResult(@NotNull Result result) {
         out.addAll(result.out);
         err.addAll(result.err);
     }

     @NotNull
     public ArrayList<String> getOutput() {
         return out;
     }

     @NotNull
     public ArrayList<String> getErrors() {
         return err;
     }
}
