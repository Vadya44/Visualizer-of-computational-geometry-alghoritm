package sample.models;

/**
 * Класс отрезков
 */
public class Line {
    /**
     * Точки отрезка
     */
    private Point p1, p2;

    /**
     * Конструктор
     * @param p1 Первая точка
     * @param p2 Вторая точка
     */
    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Функция, возвращающая длину отрезка
     */
    public double getWeight()
    {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    /**
     * Функция, возвращающая первую точку
     */
    public Point getP1() {
        return p1;
    }

    /**
     * Функция, возвращающая вторую точку
     */
    public Point getP2() {
        return p2;
    }


    /**
     * Функция, проверяющая лежит ли точка слева относительно луча vw
     * @param vw Луч
     */
    public boolean isLeftTo(Line vw)
    {
        if (!(vw.p1.equals(this.getP1()) || vw.p1.equals(this.getP2())  ||
                vw.p2.equals(this.getP1()) || vw.p2.equals(this.getP2())))
            return false;
        double k = (vw.p2.getY() - vw.p1.getY()) / (vw.p2.getX() - vw.p1.getX());
        double b = (vw.p2.getX()*vw.p1.getY() - vw.p1.getX()*vw.p2.getY())/(vw.p2.getX() - vw.p1.getX()); // для wv y = kx+b
        if (this.p2.getY() <= k * this.p2.getX() + b)
            if (this.p1.getY() <= k * this.p1.getX() + b)
                return true;
        return false;
    }

    /**
     * Функция, проверяющая лежит ли точка справа относительно луча vw
     * @param vw Луч
     */
    public boolean isRightTo(Line vw)
    {
        if (!(vw.p1.equals(this.getP1()) || vw.p1.equals(this.getP2())  ||
                vw.p2.equals(this.getP1()) || vw.p2.equals(this.getP2())))
            return false;
        double k = (vw.p2.getY() - vw.p1.getY()) / (vw.p2.getX() - vw.p1.getX());
        double b = (vw.p2.getX()*vw.p1.getY() - vw.p1.getX()*vw.p2.getY())/(vw.p2.getX() - vw.p1.getX()); // для wv y = kx+b
        if (this.p2.getY() >= k * this.p2.getX() + b)
            if (this.p1.getY() >= k * this.p1.getX() + b)
                return true;
        return false;
    }
    public boolean contains(Point p)
    {
        double dx1 = p2.getX() - p1.getX();
        double dy1 = p2.getY() - p1.getY();
        double dx = p.getX() - p1.getX();
        double dy = p.getY() - p1.getY();
        double S = dx1 * dy - dx * dy1;
        return S == 0;
    }
}
