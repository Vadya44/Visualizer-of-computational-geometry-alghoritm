package sample.models;

import java.util.HashSet;

/**
 * Синглтон - контейнер точек
 */
public class PointsSet {
    /**
     * Инстанс класса
     */
    private static final PointsSet instance = new PointsSet();
    /**
     * Хранилище точек
     */
    private HashSet<Point> mPoints;

    /**
     * Конструктор по умолчанию
     */
    private PointsSet() {
        mPoints = new HashSet<>();
    }

    /**
     * Функция, добавляющая точку по координатам
     * @param x Координата по Ox
     * @param y Координата по Oy
     */
    public static void addPoint(double x, double y)
    {
        for (Point p : instance.mPoints)
            if (p.equals(new Point(x, y)))
                return;
        instance.mPoints.add(new Point(x, y));
    }

    /**
     * Функция, удаляющая точку из хранилища
     * @param p удаляемая точка
     */
    public static void deletePoint(Point p) {
        instance.mPoints.remove(p);
    }

    /**
     * Функция, добавляющая точку по точке
     * @param p Добавляемая точка
     */
    public static void addPoint(Point p)
    {
//        boolean cont = false;
//        for (Point pp : instance.mPoints)
//            if (p.equals(pp))
//                cont = true;
//        if (!cont)
            instance.mPoints.add(p);
    }


    /**
     * Функция, возвращающая точки из хранилища
     */
    public static HashSet<Point> getPoints() {
        return instance.mPoints;
    }

    /**
     * Функция, очищающая хранилище
     */
    public static void clear()
    {
        instance.mPoints = null;
        instance.mPoints = new HashSet<>();
    }

}
