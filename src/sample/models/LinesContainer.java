package sample.models;

import java.util.HashSet;

/**
 * Синглтон класс - контейнер для отрезков
 */
public class LinesContainer {
    /**
     * Инстанс класса
     */
    private static final LinesContainer instance = new LinesContainer();
    /**
     * Хранилище отрезков
     */
    private HashSet<Line> mLines;

    /**
     * Конструктор по умолчанию
     */
    private LinesContainer() {
        mLines = new HashSet<>();
    }

    /**
     * Очистка хранилища
     */
    public static void clear() {
        instance.mLines = null;
        instance.mLines = new HashSet<>();
    }

    /**
     * Функция, добавляющая отрезок в хранилище
     * @param l Добавляемый отрезок
     */
    public static void addLine(Line l)
    {
        instance.mLines.add(l);
    }

    /**
     * Функция, возвращающая отрезки хранилища
     */
    public static HashSet<Line> getLines() {
        return instance.mLines;
    }


}
