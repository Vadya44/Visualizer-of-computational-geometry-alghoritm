package sample.models;


/**
 * Класс вершины(точки)
 */
public class Point {
    /**
     * Значения координат
     */
    private double x, y;

    /**
     * Конструктор по умолчанию
     */
    public Point(){
        x = 0;
        y = 0;
    }

    /**
     * Конструктор по координатам
     * @param x Координата по Ox
     * @param y Координата по Oy
     */
    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Функция, возвращающая значение по Ox
     */
    public double getX() {
        return x;
    }

    /**
     * Функция, возвращающая значение по Oy
     */
    public double getY() {
        return y;
    }

    /**
     * Функция, проверяющая является ли точка текущей
     * @param obj проверяемая точка
     */
    @Override
    public boolean equals(Object obj)
    {
        try {
            Point p = (Point) obj;
            return (this.getX() == p.getX() && this.getY() == p.getY());
        } catch (Exception e) {
            return false;}
    }

    /**
     * Функция, проверяющая лежит ли какая-либо точка рядом
     */
    public Point getNearest()
    {
        for (Point p : PointsSet.getPoints())
        {
            int x_curr = (int)p.getX();
            int y_curr = (int)p.getY();
            int x_self = (int)getX();
            int y_self = (int)getY();
            if (Math.abs(x_curr - x_self) <= 15 &&
                    Math.abs(y_curr - y_self) <= 15)
                return p;
        }
        return null;
    }
}
