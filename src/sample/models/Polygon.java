package sample.models;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Polygon implements Iterable {
    private List<Line> perimeter;
    private List<Point> points;

    public Polygon(List<Line> perimeter){
        this.perimeter = perimeter;
    }

    public void addLine(Line line) {
        perimeter.add(line);
        points.add(line.getP1());
    }

    public boolean isBad(Point p1, Point p2)
    {
        boolean isContains1 = false;
        boolean isContains2 = false;
        for (Line l : perimeter) {
            if (l.getP1().equals(p1))
                isContains1 = true;
            if (l.getP2().equals(p2))
                isContains2 = true;
        }
        if (isContains1 && isContains2)
        {
            boolean bad = true;
            for (Line l : perimeter)
                if (l.getP1().equals(p1) && l.getP2().equals(p2)
                        || l.getP1().equals(p2) && l.getP2().equals(p1))
                    bad = false;
            return bad;
        }
        return false;
    }

    public Polygon() {
        perimeter = new ArrayList<>();
        points = new ArrayList<>();
    };

    public boolean contains(Point test) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if ((points.get(i).getY() > test.getY()) != (points.get(j).getY() > test.getY()) &&
                    (test.getX() < (points.get(j).getX() - points.get(i).getX()) *
                            (test.getY() - points.get(i).getY()) /
                            (points.get(j).getY()-points.get(i).getY()) + points.get(i).getX())) {
                result = !result;
            }
        }
        return result;
    }

    @Override
    public Iterator<Line> iterator() {
        if (perimeter.isEmpty())
            throw new NullPointerException("Empty perimeter");
        return perimeter.iterator();
    }

    @Override
    public void forEach(Consumer action) {
        if (perimeter.isEmpty())
            throw new NullPointerException("Empty perimeter");
        for (Line l : perimeter)
            action.accept(l);
    }

    public double[] getX()
    {
        double[] res = new double[points.size()];
        int i = 0;
        for (Point p : points)
            res[i++] = p.getX();
        return res;
    }
    public double[] getY()
    {
        double[] res = new double[points.size()];
        int i = 0;
        for (Point p : points)
            res[i++] = p.getY();
        return res;
    }

    public int pointsSize()
    {
        return points.size();
    }

}
