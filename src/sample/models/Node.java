package sample.models;

/**
 * Класс узла дерева
 */
public class Node {
    /**
     * Правый ребенок
     */
    public Node right;
    /**
     * Левый ребенок
     */
    public Node left;
    /**
     * Ключ-отрезок узла
     */
    public Line key;
    /**
     * Дистанция, по которой сортировка в BST происходит
     */
    public double d;

    /**
     * Конструктор узла
     * @param l Ключ-отрезок
     * @param dist Дистанция
     */
    public Node(Line l, double dist)
    {
        key = l;
        d = dist;
        left = right = null;
    }
}
