package sample.models;

/**
 *
 */
public class Node {
    /**
     *
     */
    public Node right;
    /**
     *
     */
    public Node left;
    /**
     *
     */
    public Line key;
    /**
     *
     */
    public double d;

    /**
     *
     * @param l
     * @param dist
     */
    public Node(Line l, double dist)
    {
        key = l;
        d = dist;
        left = right = null;
    }
}
