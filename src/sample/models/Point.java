package sample.models;


/**
 *
 */
public class Point {
    /**
     *
     */
    private double x, y;

    /**
     *
     */
    public Point(){
        x = 0;
        y = 0;
    }

    /**
     *
     * @param x
     * @param y
     */
    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        try {
            Point p = (Point) obj;
            return (this.getX() == p.getX() && this.getY() == p.getY());
        } catch (Exception e) {
            return false;}
    }

    /**
     *
     * @return
     */
    public Point getNearest()
    {
        for (Point p : PointsSet.getPoints())
        {
            int x_curr = (int)p.getX();
            int y_curr = (int)p.getY();
            int x_self = (int)getX();
            int y_self = (int)getY();
            if (Math.abs(x_curr - x_self) <= 15 &&
                    Math.abs(y_curr - y_self) <= 15)
                return p;
        }
        return null;
    }
}
