package sample.models;

import java.util.ArrayList;

/**
 * Синглтон - контейнер полигонов
 */
public class PolygonContainer {
    /**
     * Хранилище полигонов
     */
    private ArrayList<Polygon> mPolygons;

    /**
     * Инстанс класса
     */
    private static final PolygonContainer instance = new PolygonContainer();

    /**
     * Функция, проверяющая, лежит ли отрезок внутри какого-либо полигона
     * @param p1 Первая точка
     * @param p2 Вторая точка
     */
    public static boolean isOkay(Point p1, Point p2)
    {
        for (Polygon poly : instance.mPolygons)
            if (poly.isBad(p1, p2))
                return true;
        return false;
    }

    /**
     * Конструктор по умолчанию
     */
    private PolygonContainer()
    {
        mPolygons = new ArrayList<>();
    }

    /**
     * Функция, очищающая контейнер
     */
    public static void clear()
    {
        instance.mPolygons = null;
        instance.mPolygons = new ArrayList<>();
    }

    /**
     * Функция, проверяющая, является ли точка вершиной какого-либо полигона
     * @param p Проверяемая точка
     */
    public static boolean contains(Point p)
    {
        for (Polygon polygon : instance.mPolygons)
            if (polygon.contains(p)) return true;
        return false;
    }

    /**
     * Функция, добавляющая полигон в контейнер
     * @param p Добавляемый полигон
     */
    public static void addPolygon(Polygon p)
    {
        instance.mPolygons.add(p);
    }
}
