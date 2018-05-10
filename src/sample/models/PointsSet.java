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
//        boolean cont = false;
//        for (Point pp : instance.mPoints)
//            if (p.equals(pp))
//                cont = true;
//        if (!cont)
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

}
