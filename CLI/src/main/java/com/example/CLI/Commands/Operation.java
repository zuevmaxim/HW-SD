package com.example.CLI.Commands;

/**
 * Каждая сущность в языке (в том числе и литералы) наследуется от этого интерфейса.
 */
public interface Operation {

    /**
     * Исполняет код, соответствующий его сущности в языке.
     * @return объект класса Result с результатом работы
     */
    Result execute();
}
