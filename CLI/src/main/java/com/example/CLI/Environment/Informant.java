package com.example.CLI.Environment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Интерфейс класса "информатор", описывающего обмен данными.
 */
public interface Informant {

    /**
     * Создает буфер памяти для обмена данными с некоторым уникальным тегом.
     * @return тег, соответстующий выделенному буферу обмена.
     */
    @NotNull
    String createConnection();

    /**
     * Записывает данные в имеющийся буфер. Если нужного буфера нет, записывает данные в файл.
     * @param name - тег буфера
     * @param data - данные
     * @throws IOException - если запись в файл невозможна
     */
    void send(@NotNull String name, byte[] data) throws IOException;

    /**
     * Возвращает данные, содержащиеся в буфере. Если нужного буфера нет, ищет файл с соответствующим именем и берет данные из него.
     * @param name - тег буфера
     * @return массив байты
     * @throws IOException - если чтение из файла невозможно
     */
    byte[] getAndClose(@NotNull String name) throws IOException;
}
