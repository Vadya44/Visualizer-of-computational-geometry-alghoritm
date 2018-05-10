package sample.models;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 */
public class Polygon implements Iterable {
    /**
     *
     */
    private List<Line> perimeter;
    /**
     *
     */
    private List<Point> points;


    /**
     *
     * @param line
     */
    public void addLine(Line line) {
        perimeter.add(line);
        points.add(line.getP1());
    }

    /**
     *
     * @param p1
     * @param p2
     * @return
     */
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
        Point middle = new Point(((p1.getX() + p2.getX()) / 2), (p1.getY() + p2.getY()) / 2);
        return (contains(middle));
    }

    /**
     *
     */
    public Polygon() {
        perimeter = new ArrayList<>();
        points = new ArrayList<>();
    }

    /**
     *
     * @param test
     * @return
     */
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

    /**
     *
     * @return
     */
    @Override
    public Iterator<Line> iterator() {
        if (perimeter.isEmpty())
            throw new NullPointerException("Empty perimeter");
        return perimeter.iterator();
    }

    /**
     *
     * @param action
     */
    @Override
    public void forEach(Consumer action) {
        if (perimeter.isEmpty())
            throw new NullPointerException("Empty perimeter");
        for (Line l : perimeter)
            action.accept(l);
    }

    /**
     *
     * @return
     */
    public double[] getX()
    {
        double[] res = new double[points.size()];
        int i = 0;
        for (Point p : points)
            res[i++] = p.getX();
        return res;
    }

    /**
     *
     * @return
     */
    public double[] getY()
    {
        double[] res = new double[points.size()];
        int i = 0;
        for (Point p : points)
            res[i++] = p.getY();
        return res;
    }

    /**
     *
     * @return
     */
    public int pointsSize()
    {
        return points.size();
    }

}
