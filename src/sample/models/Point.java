package sample.models;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Point {
    private double x, y;

    public Point(){
        x = 0;
        y = 0;
    }

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Set<Line> getLines()
    {
        Set<Line> res = new HashSet<>();
        for (Line l : LinesContainer.getLines())
        {
            if ((l.getP1().getX() == x &&
                    l.getP1().getY() == y) ||
                    (l.getP2().getX() == x &&
                            l.getP2().getY() == y))
                res.add(l);
        }
        return res;
    }
    public Point getPrev()
    {
        Point p, temp = null;
        Iterator<Point> iterator = Graph.sortedPoints.iterator();
        while(iterator.hasNext())
        {
            p = iterator.next();
            if (p.getX() == this.getX() && p.getY() == this.getY())
                break;
            else temp = p;
        }
        return temp;
    }
    @Override
    public boolean equals(Object obj)
    {
        try {
            Point p = (Point) obj;
            return (this.getX() == p.getX() && this.getY() == p.getY());
        } catch (Exception e) {
            return false;}
    }

    public Point getNearest()
    {
        for (Point p : PointsSet.getPoints())
        {
            int x_curr = (int)p.getX();
            int y_curr = (int)p.getY();
            int x_self = (int)getX();
            int y_self = (int)getY();
            if (Math.abs(x_curr - x_self) <= 50 &&
                    Math.abs(y_curr - y_self) <= 50)
                return p;
        }
        return null;
    }
}
