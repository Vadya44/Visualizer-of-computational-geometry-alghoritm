package sample.models;

public class Line {
    private Point p1, p2;

    public Line(){
        p1 = new Point();
        p2 = new Point();
    }

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public double getWeight()
    {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }
    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public boolean contains(Point p)
    {
        double k = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
        double b = (p2.getX()*p1.getY() - p1.getX()*p2.getY())/(p2.getX() - p1.getX()); // y = kx+b
        if (p.getY() == k * p.getX() + b)
            return true;
        else return false;
    }
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
