package sample.models;

import java.util.HashSet;

/**
 *
 */
public class PointsSet {
    /**
     *
     */
    private static final PointsSet instance = new PointsSet();
    /**
     *
     */
    private HashSet<Point> mPoints;

    /**
     *
     */
    private PointsSet() {
        mPoints = new HashSet<>();
    }

    /**
     *
     * @param x
     * @param y
     */
    public static void addPoint(double x, double y)
    {
        for (Point p : instance.mPoints)
            if (p.equals(new Point(x, y)))
                return;
        instance.mPoints.add(new Point(x, y));
    }

    /**
     *
     * @param p
     */
    public static void deletePoint(Point p) {
        instance.mPoints.remove(p);
    }

    /**
     *
     * @param p
     */
    public static void addPoint(Point p)
    {
//        boolean cont = false;
//        for (Point pp : instance.mPoints)
//            if (p.equals(pp))
//                cont = true;
//        if (!cont)
            instance.mPoints.add(p);
    }


    /**
     *
     * @return
     */
    public static HashSet<Point> getPoints() {
        return instance.mPoints;
    }

    /**
     *
     */
    public static void clear()
    {
        instance.mPoints = null;
        instance.mPoints = new HashSet<>();
    }

}
