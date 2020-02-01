package com.example.CLI.Environment;

import org.jetbrains.annotations.NotNull;

/**
 * Объекты, работающие c информатором.
 */
public interface Informed {

    /**
     * Информирует класс о том, что ему доступны данные в буфере информатора.
     * @param name тег буфера
     */
    void addConnection(@NotNull String name);
}
