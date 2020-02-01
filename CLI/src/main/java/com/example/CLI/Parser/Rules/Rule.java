package com.example.CLI.Parser.Rules;

import com.example.CLI.Commands.Operation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Описывает правило распознавания отдельной сущности языка.
 */
public interface Rule {

    /**
     * Тип правила.
     * {@link Type#WORD} - правило для литералов
     * {@link Type#COMMAND} - правило для комманд
     * {@link Type#SPECIAL} - правило для других операций
     */
    public enum Type {
        WORD,
        COMMAND,
        SPECIAL
    }

    /**
     * Возвращает тип правила.
     */
    @NotNull
    Type getType();

    /**
     * Возвращает уровень правила - парсер распознает сначала правила с больший уровнем.
     */
    @NotNull
    Integer getLevel();

    /**
     * Проверяет, совпадает ли ввод с соответствующей сущностью в языке.
     * @param input - строка ввода
     * @return true, если совпадает
     */
    boolean isMatching(@NotNull String input);

    /**
     * Возвращает строки, соответствующие аргументам сущности.
     * @param input - строка ввода
     * @return ArrayList со строками аргументов
     */
    @NotNull
    ArrayList<String> split(@NotNull String input);

    /**
     * Создает оъект класса соответствующей сущности.
     * @param args - аргументы (объекты Operation) в том же порядке, что вернул {@link Rule#split}
     * @return оъект класса соответствующей сущности
     */
    @NotNull
    Operation createOperation(ArrayList<Operation> args);
}
