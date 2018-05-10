package sample.models;

import java.util.ArrayList;
import java.util.Set;

/**
 * Бинарное дерево поиска для ребер, упорядоченных по кратчайшему расстоянию от точки v
 */
public class BST {
    /**
     * Корень дерева
     */
    Node root;
    /**
     * Массив ребер
     */
    private ArrayList<Line> mLines;


    /**
     * Конструктор с последующей вставкой передаваемых отрезков
     * @param v Точка, относительно которой ищутся расстояния
     * @param lines Ребра, подлежащие вставке в дерево
     */
    public BST(Point v, Set<Line> lines)
    {
        for (Line l : lines)
        {
            if (l.getP1().getX() < v.getX() && v.getX() < l.getP2().getX()) {
                double x = v.getX();
                double a = (l.getP1().getY() - l.getP2().getY()) / (l.getP1().getX() - l.getP2().getX());
                double b = (l.getP2().getX() * l.getP1().getY() - l.getP1().getX() *
                        l.getP2().getY())/(l.getP2().getX() - l.getP1().getX()); // y = ax+b
                double y = a * x + b;
                insert(l, Math.abs(y - v.getY()));
            }
        }
    }

    /**
     * Функция, вычисляющая кратчайшее расстояние от точки до отрезка
     * @param v Точка, от которой ищется расстояние
     * @param l Отрезок, к которому ищется расстояние
     */
    public int getDist(Point v, Line l)
    {
        double perp, vp1, vp2;
        double a = (l.getP1().getY() - l.getP2().getY()) / (l.getP1().getX() - l.getP2().getX());
        double b = (l.getP2().getX() * l.getP1().getY() - l.getP1().getX() *
                l.getP2().getY())/(l.getP2().getX() - l.getP1().getX()); // y = ax+b
        perp = Math.abs((-a) * v.getX() + v.getY() - b) / Math.sqrt(1 + (a * a));

        vp1 = Math.sqrt((v.getX() - l.getP1().getX())*(v.getX() - l.getP1().getX()) +
                (v.getY() - l.getP1().getY())*(v.getY() - l.getP1().getY()));
        vp2  = Math.sqrt((v.getX() - l.getP2().getX())*(v.getX() - l.getP2().getX()) +
                (v.getY() - l.getP2().getY())*(v.getY() - l.getP2().getY()));
        int c = (int)Math.max(vp1, vp2);
        int bb = (int)Math.min(vp1, vp2);
        if (c * c >=  bb * bb + perp * perp + 5)
            perp = Integer.MAX_VALUE;
        return (int)Math.min(perp, Math.min(vp1, vp2));

    }

    /**
     * Функция вставки передаваемого отрезка в дерево
     * @param v Точка, от которой ищется расстояние
     * @param l Отрезок, подлежащий вставке
     */
    public void add(Point v, Line l)
    {
        int dist = getDist(v, l);
        if (dist > 0)
            insert(l, dist);
    }

    /**
     * Функция удаления из дерева передаваемого отрезка
     * @param v Точка, от которой ищется расстояние
     * @param l Отрезок, подлежащий удалению
     */
    public void remove(Point v, Line l)
    {
        root = deleteRec(v, root, l);
    }

    /**
     * Вспомогательная рекурсивная функция удаления
     * @param v Опорная точка для вычислений
     * @param root Текущий узел дерева
     * @param key Удаляемый отрезок
     */
    Node deleteRec(Point v, Node root, Line key)
    {
        if (root == null)  return root;

        if (getDist(v, key) < getDist(v, root.key))
            root.left = deleteRec(v, root.left, key);
        else if (getDist(v, key) > getDist(v, root.key))
            root.right = deleteRec(v, root.right, key);

        else
        {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            root.key = minValue(root.right);

            root.right = deleteRec(v, root.right, root.key);
        }

        return root;
    }

    /**
     * Нахождение минимального(кратчайшего левого) листа
     * @param root
     */
    Line minValue(Node root)
    {
        Line minv = root.key;
        while (root.left != null)
        {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    /**
     * Вставка по отрезку без опорной точки
     * @param p Встваляемый отрезок
     * @param dist Дистанция для сортировки
     */
    void insert(Line p, double dist) {
        root = insertRec(root, p, dist);
    }

    /**
     * Рекурсивная вспомогательная функция для вставки
     * @param root Текущий узел дерева
     * @param p Вставляемый отрезок
     * @param dist Дистанция для сортировки
     */

    Node insertRec(Node root, Line p, double dist) {

        if (root == null) {
            root = new Node(p, dist);
            return root;
        }

        if (dist <= root.d)
            root.left = insertRec(root.left, p, dist);
        else
            root.right = insertRec(root.right, p, dist);

        return root;
    }


    /**
     * Функция нахождения площади
     * @param a Точка А
     * @param b Точка B
     * @param c Точка C
     */
    static long area(Point a, Point b, Point c)
    {
        return (long)((b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX()));
    }

    static boolean intersect_1(int a, int b, int c, int d)
    {
        if (a > b){
            int buf = a;
            a = b;
            b = buf;
        }
        if (c > d)
        {
            int buf = c;
            c = d;
            d = buf;
        }
        return Math.max(a, c) <= Math.min(d, b);
    }


    /**
     * Взятие крайнего левого листа
     */
    public Line getLeft()
    {
        Node temp = root;
        if (temp == null) return null;
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp.key;
    }
}
