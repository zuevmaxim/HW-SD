package com.example.CLI.Environment;

import com.example.CLI.Commands.Command;
import com.example.CLI.Commands.Operation;
import org.jetbrains.annotations.NotNull;

/**
 * Контекст выполнения комманд.
 * Содержит в себе переменные окружения и внутренние команды.
 */
public interface Context {

    /**
     * Создает новую переменную или устанавливает новое значение для имеющейся.
     * @param name - переменная
     * @param value - новое значение
     */
    void setValue(String name, Operation value);

    /**
     * Возвращает значение переменной окружения.
     * @param name - переменная
     * @return значение переменной
     */
    @NotNull
    Operation getValue(@NotNull String name);

    /**
     * Возвращает команду по ее имени.
     * @param name - имя команды
     * @return объект класса, реализующий интерфейс Command
     */
    @NotNull
    Command getCommand(@NotNull String name);
}
