package sample.models;

import java.util.*;

public class Dijkstra {

    private final List<Point> nodes;
    private final List<Line> Lines;
    private Set<Point> settledNodes;
    private Set<Point> unSettledNodes;
    private Map<Point, Point> predecessors;
    private Map<Point, Integer> distance;

    public Dijkstra(Graph graph) {

        this.nodes = new ArrayList<Point>(PointsSet.getPoints());
        this.Lines = new ArrayList<Line>(graph.getEdges());
        this.Lines.addAll(getSecondPath(graph.getEdges()));
    }

    public static ArrayList<Line> getSecondPath(ArrayList<Line> lines)
    {
        ArrayList<Line> res = new ArrayList<>();
        for (Line l : lines)
            res.add(new Line(l.getP2(), l.getP1()));
        return res;
    }

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

    private int getDistance(Point node, Point target) {
        for (Line line : Lines) {
            if (line.getP1().equals(node) &&
                    line.getP2().equals(target) ) {
                return (int)line.getWeight();
            }
        }
        return 0;
    }

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

    private boolean isSettled(Point Point) {
        return settledNodes.contains(Point);
    }

    private int getShortestDistance(Point destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

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