package sample.models;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс полигона
 */
public class Polygon implements Iterable {
    /**
     * Хранилище ребер полигона
     */
    private List<Line> perimeter;
    /**
     * Хранилище вершин полигона
     */
    private List<Point> points;


    /**
     * Функция, добавляющая ребро
     * @param line Добавляемое ребро
     */
    public void addLine(Line line) {
        perimeter.add(line);
        points.add(line.getP1());
    }

    /**
     * Функция, проверяющая, лежит ли отрезок внутри полигона
     * @param p1 Первая точка
     * @param p2 Вторая точка
     */
    public boolean isBad(Point p1, Point p2)
    {
        boolean isContains1 = false;
        boolean isContains2 = false;
        for (Line l : perimeter) {
            if (l.getP1().equals(p1))
                isContains1 = true;
            if (l.getP2().equals(p2))
                isContains2 = true;
        }
        if (isContains1 && isContains2)
        {
            boolean bad = true;
            for (Line l : perimeter)
                if (l.getP1().equals(p1) && l.getP2().equals(p2)
                        || l.getP1().equals(p2) && l.getP2().equals(p1))
                    bad = false;
            return bad;
        }
        Point middle = new Point(((p1.getX() + p2.getX()) / 2), (p1.getY() + p2.getY()) / 2);
        return (contains(middle));
    }

    /**
     * Конструктор по умолчанию
     */
    public Polygon() {
        perimeter = new ArrayList<>();
        points = new ArrayList<>();
    }

    /**
     * Функция, проверяющая, лежит ли точка внутри полигона
     * @param test Проверяемая точка
     */
    public boolean contains(Point test) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if ((points.get(i).getY() > test.getY()) != (points.get(j).getY() > test.getY()) &&
                    (test.getX() < (points.get(j).getX() - points.get(i).getX()) *
                            (test.getY() - points.get(i).getY()) /
                            (points.get(j).getY()-points.get(i).getY()) + points.get(i).getX())) {
                result = !result;
            }
        }
        return result;
    }

    /**
     * Итератор по ребрам
     */
    @Override
    public Iterator<Line> iterator() {
        if (perimeter.isEmpty())
            throw new NullPointerException("Empty perimeter");
        return perimeter.iterator();
    }


    /**
     * Функция, возвращающая значения всех точек по Ox
     */
    public double[] getX()
    {
        double[] res = new double[points.size()];
        int i = 0;
        for (Point p : points)
            res[i++] = p.getX();
        return res;
    }

    /**
     * Функция, возвращающая значения всех точек по Oy
     */
    public double[] getY()
    {
        double[] res = new double[points.size()];
        int i = 0;
        for (Point p : points)
            res[i++] = p.getY();
        return res;
    }

    /**
     * Функция, возвращающая количество вершин полигона
     */
    public int pointsSize()
    {
        return points.size();
    }

}
