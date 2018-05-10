package sample.models;

import java.util.*;

/**
 * Поиск кратчайшего расстояние из точки А в точку Б на графе
 */
public class Dijkstra {

    /**
     * Массив ребер
     */
    private final List<Line> Lines;
    /**
     * Набор уже решенных точек
     */
    private Set<Point> settledNodes;
    /**
     * Набор еще не решенных точек
     */
    private Set<Point> unSettledNodes;
    /**
     * Карта навигации по предшественникам
     */
    private Map<Point, Point> predecessors;
    /**
     * Набор точка-расстояние
     */
    private Map<Point, Integer> distance;

    /**
     * Конструктор по графу
     * @param graph Граф подлежащий решению
     */
    public Dijkstra(Graph graph) {

        this.Lines = new ArrayList<Line>(graph.getEdges());
        this.Lines.addAll(getSecondPath(graph.getEdges()));
    }

    /**
     * Функция, дублирующая ребра, разворачивая, чтобы они стали двунаправленными
     * @param lines дублируемые ребра
     */
    public static ArrayList<Line> getSecondPath(ArrayList<Line> lines)
    {
        ArrayList<Line> res = new ArrayList<>();
        for (Line l : lines)
            res.add(new Line(l.getP2(), l.getP1()));
        return res;
    }

    /**
     * Инициализация на основе точки
     * @param source Начальная точка
     */
    public void execute(Point source) {
        settledNodes = new HashSet<Point>();
        unSettledNodes = new HashSet<Point>();
        distance = new HashMap<Point, Integer>();
        predecessors = new HashMap<Point, Point>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Point node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    /**
     * Запуск поиска кратчайшего расстояния для текущей точки
     * @param node Точка относительно которой ищется кратчайшее расстояние
     */
    private void findMinimalDistances(Point node) {
        List<Point> adjacentNodes = getNeighbors(node);
        for (Point target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target))
            {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);

            }
        }

    }

    /**
     * Получение дистанции из node в target
     * @param node Начальная точка
     * @param target Конечная точка
     */
    private int getDistance(Point node, Point target) {
        for (Line line : Lines) {
            if (line.getP1().equals(node) &&
                    line.getP2().equals(target) ) {
                return (int)line.getWeight();
            }
        }
        return 0;
    }

    /**
     * Получение массива соседей точки
     * @param node Точка, соседи которой будут искаться
     */
    private List<Point> getNeighbors(Point node) {
        List<Point> neighbors = new ArrayList<Point>();
        for (Line line : Lines) {
            if (line.getP1().equals(node)
                    && !isSettled(line.getP2())) {
                neighbors.add(line.getP2());
            }
        }
        return neighbors;
    }

    /**
     * Функция находящая минимум в передаваемом массиве точек
     * @param Pointes Набор точек в которых будет искаться минимальная
     */
    private Point getMinimum(Set<Point> Pointes) {
        Point minimum = null;
        for (Point Point : Pointes) {
            if (minimum == null) {
                minimum = Point;
            } else {
                if (getShortestDistance(Point) < getShortestDistance(minimum)) {
                    minimum = Point;
                }
            }
        }
        return minimum;
    }

    /**
     * Проверка на решенность точки
     * @param Point Проверяемая точка
     */
    private boolean isSettled(Point Point) {
        return settledNodes.contains(Point);
    }

    /**
     * Возвращает кратчайшее расстояние до точки
     * @param destination Конечная точка
     */
    private int getShortestDistance(Point destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /**
     * Взятие кратчайшего пути до точки(связный список)
     * @param target Конечная точка
     */
    public LinkedList<Point> getPath(Point target) {
        LinkedList<Point> path = new LinkedList<Point>();
        Point step = target;
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }

}