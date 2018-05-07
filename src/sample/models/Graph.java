package sample.models;

import java.util.*;

public class Graph {

    public static TreeSet<Point> sortedPoints;

    private static Set<Line> mLines;

    public ArrayList<Line> getEdges() {
        return mEdges;
    }

    public ArrayList<Point> getVertexes() {
        return mVertexes;
    }


    ArrayList<Line> mEdges;
    ArrayList<Point> mVertexes;


    private static boolean areIntersectsLines(Line line, Line left) {
        boolean isOk = true;
        for (Line l : mLines)
            if (areIntersects(line, l))
                isOk = false;
        return isOk;
    }

    public static boolean areIntersects(Line l1, Line l2)
    {
        if (l1 == null || l2 == null) return false;
        if (l1.getP2().equals(l2.getP1()) || l1.getP2().equals(l2.getP2())
                || l1.getP1().equals(l2.getP1()) || l1.getP1().equals(l2.getP2()))
            return false;

        int a_x = (int)l1.getP1().getX();
        int a_y = (int)l1.getP1().getY();
        int b_x = (int)l1.getP2().getX();
        int b_y = (int)l1.getP2().getY();
        int c_x = (int)l2.getP1().getX();
        int c_y = (int)l2.getP1().getY();
        int d_x = (int)l2.getP2().getX();
        int d_y = (int)l2.getP2().getY();
        Point a = l1.getP1();
        Point b = l1.getP2();
        Point c = l2.getP1();
        Point d = l2.getP2();
        boolean areas1 = false;
        boolean areas2 = false;
        if (BST.area(a, b, c) *  BST.area(a,b,d) <= 0)
            areas1 = true;
        if (BST.area(c,d,a) * BST.area(c,d,b) <= 0)
            areas2 = true;

        boolean res = BST.intersect_1(a_x, b_x, c_x, d_x)
                && BST.intersect_1(a_y, b_y, c_y, d_y)
                && areas1
                && areas2;
        return res;

    }

    private void addEdge(Point p, Point p2)
    {
        if (mEdges == null)
            mEdges = new ArrayList<>();
        mEdges.add(new Line(p, p2));
    }


    public Graph(Set<Point> points)
    {
        this.mVertexes = new ArrayList<>(points);
    }



    public static Graph buildVisibilityGraph(HashSet<Line> lines)
    {
        Graph graph = new Graph(PointsSet.getPoints());
        for (Point v : graph.getVertexes())
        {
            HashSet<Point> visibleVerticies = getVisibleVerticies(v, lines);
            for (Point w : visibleVerticies)
                if (!v.equals(w) && !PolygonContainer.isOkay(v, w))
                    graph.addEdge(v, w);
        }

        return graph;
    }



    public static int getAngle(Point p1, Point p2)
    {
        double a = Math.abs(p1.getX() - p2.getX());
        double b = Math.abs(p1.getY() - p2.getY());
        int angle = (int)(Math.toDegrees(Math.atan(a/b)) * 10);
        if (p1.getY() < p2.getY())
            return angle;
        else
            return 1800 - angle;

    }

    private static HashSet<Point> getVisibleVerticies(Point v, Set<Line> lines)
    {
        Comparator<Point> comparator = (Point o1, Point o2) -> getAngle(v, o2) - getAngle(v, o1);
        mLines = lines;
        sortedPoints = new TreeSet<Point>(comparator);
        HashSet<Point> res = new HashSet<>();
        BST status = new BST(v, lines);
        for (Point w : PointsSet.getPoints())
        {
            if (v.getX() <= w.getX())
                sortedPoints.add(w);
        }
        for (Point w : sortedPoints)
        {
            if (areIntersectsLines(new Line(v, w), status.getLeft()))
                res.add(w);

            for (Line s : lines)
                if (s.isLeftTo(new Line (v, w)))
                    status.remove(v, s);
            for (Line s : lines)
                if (s.isRightTo(new Line (v, w)))
                    status.add(v, s);
        }
        return res;
    }


}
