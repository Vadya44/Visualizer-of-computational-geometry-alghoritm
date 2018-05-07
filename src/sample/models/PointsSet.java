package sample.models;

import java.util.HashSet;
import java.util.Set;

public class PointsSet {
    private static final PointsSet instance = new PointsSet();
    private HashSet<Point> mPoints;

    private PointsSet() {
        mPoints = new HashSet<>();
    }

    public static void addPoint(double x, double y)
    {
        boolean isContains = false;
        for (Point p : instance.mPoints)
            if (p.equals(new Point(x, y)))
                return;
        instance.mPoints.add(new Point(x, y));
    }

    public static void deletePoint(Point p) {
        instance.mPoints.remove(p);
    }

    public static void addPoint(Point p)
    {
        instance.mPoints.add(p);
    }

    public static PointsSet getInstance()
    {
        return instance;
    }

    public static HashSet<Point> getPoints() {
        return instance.mPoints;
    }

    public static void clear()
    {
        instance.mPoints = null;
        instance.mPoints = new HashSet<>();
    }

    public static void setPoints(HashSet<Point> points) {
        instance.mPoints = points;
    }

    public static Point contains(double x, double y)
    {
        double xH = x + 100;
        double xM = x - 100;
        double yH = y + 100;
        double yM = y - 100;
        for (Point p : instance.mPoints)
        {
            double tempX = p.getX();
            double tempY = p.getY();
            if (tempX >= xM && tempX <= xH &&
                    tempY >= yM && tempY <= yH)
                return p;
        }
        return null;
    }
}
