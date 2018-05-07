package sample.models;

import java.util.*;

public class Graph {

    public static TreeSet<Point> sortedPoints;

    public ArrayList<Line> getEdges() {
        return mEdges;
    }

    public ArrayList<Point> getVertexes() {
        return mVertexes;
    }


    ArrayList<Line> mEdges;
    ArrayList<Point> mVertexes;



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
            boolean isOk = true;
            for (Line l : status.getLines())
                if (BST.areIntersects(new Line(v, w), l))
                    isOk = false;
            if (isOk) res.add(w);
//            if (!BST.areIntersects(new Line(v, w), status.getLeft()))
//               res.add(w);

            // TODO : при равном расстоянии из статуса берется иной left

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
