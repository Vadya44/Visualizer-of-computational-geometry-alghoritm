package sample.models;

import java.util.ArrayList;

public class PolygonContainer {
    private ArrayList<Polygon> mPolygons;

    private static final PolygonContainer instance = new PolygonContainer();

    public static boolean isOkay(Point p1, Point p2)
    {
        for (Polygon poly : instance.mPolygons)
            if (poly.isBad(p1, p2))
                return true;
        return false;
    }

    private PolygonContainer()
    {
        mPolygons = new ArrayList<>();
    }

    public static void clear()
    {
        instance.mPolygons = null;
        instance.mPolygons = new ArrayList<>();
    }

    public static boolean contains(Point p)
    {
        for (Polygon polygon : instance.mPolygons)
            if (polygon.contains(p)) return true;
        return false;
    }

    public static void addPolygon(Polygon p)
    {
        instance.mPolygons.add(p);
    }
}
