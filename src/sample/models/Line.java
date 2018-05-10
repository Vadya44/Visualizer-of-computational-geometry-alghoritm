package sample.models;

/**
 *
 */
public class Line {
    /**
     *
     */
    private Point p1, p2;

    /**
     *
     * @param p1
     * @param p2
     */
    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     *
     * @return
     */
    public double getWeight()
    {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    /**
     *
     * @return
     */
    public Point getP1() {
        return p1;
    }

    /**
     *
     * @return
     */
    public Point getP2() {
        return p2;
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean contains(Point p)
    {
        double k = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
        double b = (p2.getX()*p1.getY() - p1.getX()*p2.getY())/(p2.getX() - p1.getX()); // y = kx+b
        if (p.getY() == k * p.getX() + b)
            return true;
        else return false;
    }

    /**
     *
     * @param vw
     * @return
     */
    public boolean isLeftTo(Line vw)
    {
        if (!(vw.p1.equals(this.getP1()) || vw.p1.equals(this.getP2())  ||
                vw.p2.equals(this.getP1()) || vw.p2.equals(this.getP2())))
            return false;
        double k = (vw.p2.getY() - vw.p1.getY()) / (vw.p2.getX() - vw.p1.getX());
        double b = (vw.p2.getX()*vw.p1.getY() - vw.p1.getX()*vw.p2.getY())/(vw.p2.getX() - vw.p1.getX()); // для wv y = kx+b
        if (this.p2.getY() <= k * this.p2.getX() + b)
            if (this.p1.getY() <= k * this.p1.getX() + b)
                return true;
        return false;
    }

    /**
     *
     * @param vw
     * @return
     */
    public boolean isRightTo(Line vw)
    {
        if (!(vw.p1.equals(this.getP1()) || vw.p1.equals(this.getP2())  ||
                vw.p2.equals(this.getP1()) || vw.p2.equals(this.getP2())))
            return false;
        double k = (vw.p2.getY() - vw.p1.getY()) / (vw.p2.getX() - vw.p1.getX());
        double b = (vw.p2.getX()*vw.p1.getY() - vw.p1.getX()*vw.p2.getY())/(vw.p2.getX() - vw.p1.getX()); // для wv y = kx+b
        if (this.p2.getY() >= k * this.p2.getX() + b)
            if (this.p1.getY() >= k * this.p1.getX() + b)
                return true;
        return false;
    }
}
