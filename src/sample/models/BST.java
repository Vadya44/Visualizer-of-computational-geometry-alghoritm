package sample.models;

import java.util.ArrayList;
import java.util.Set;


public class BST {
    Node root;

    private ArrayList<Line> mLines;

    public BST(){
        root = null;
    }

    public BST(Point v, Set<Line> lines)
    {
        for (Line l : lines)
        {
            if (l.getP1().getX() < v.getX() && v.getX() < l.getP2().getX()) {
                double x = v.getX();
                double a = (l.getP1().getY() - l.getP2().getY()) / (l.getP1().getX() - l.getP2().getX());
                double b = (l.getP2().getX() * l.getP1().getY() - l.getP1().getX() *
                        l.getP2().getY())/(l.getP2().getX() - l.getP1().getX()); // y = ax+b
                double y = a * x + b;
                insert(l, Math.abs(y - v.getY()));
            }
        }
    }

    public int getDist(Point v, Line l)
    {
        double perp, vp1, vp2;
        double a = (l.getP1().getY() - l.getP2().getY()) / (l.getP1().getX() - l.getP2().getX());
        double b = (l.getP2().getX() * l.getP1().getY() - l.getP1().getX() *
                l.getP2().getY())/(l.getP2().getX() - l.getP1().getX()); // y = ax+b
        perp = Math.abs((-a) * v.getX() + v.getY() - b) / Math.sqrt(1 + (a * a));

        vp1 = Math.sqrt((v.getX() - l.getP1().getX())*(v.getX() - l.getP1().getX()) +
                (v.getY() - l.getP1().getY())*(v.getY() - l.getP1().getY()));
        vp2  = Math.sqrt((v.getX() - l.getP2().getX())*(v.getX() - l.getP2().getX()) +
                (v.getY() - l.getP2().getY())*(v.getY() - l.getP2().getY()));
        int c = (int)Math.max(vp1, vp2);
        int bb = (int)Math.min(vp1, vp2);
        if (c * c >=  bb * bb + perp * perp + 5)
            perp = Integer.MAX_VALUE;
        return (int)Math.min(perp, Math.min(vp1, vp2));

    }

    public void add(Point v, Line l)
    {
        int dist = getDist(v, l);
        if (dist > 0)
            insert(l, dist);
    }

    public void remove(Point v, Line l)
    {
        root = deleteRec(v, root, l);
    }


    Node deleteRec(Point v, Node root, Line key)
    {
        /* Base Case: If the tree is empty */
        if (root == null)  return root;

        /* Otherwise, recur down the tree */
        if (getDist(v, key) < getDist(v, root.key))
            root.left = deleteRec(v, root.left, key);
        else if (getDist(v, key) > getDist(v, root.key))
            root.right = deleteRec(v, root.right, key);

            // if key is same as root's key, then This is the node
            // to be deleted
        else
        {
            // node with only one child or no child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root.key = minValue(root.right);

            // Delete the inorder successor
            root.right = deleteRec(v, root.right, root.key);
        }

        return root;
    }
    Line minValue(Node root)
    {
        Line minv = root.key;
        while (root.left != null)
        {
            minv = root.left.key;
            root = root.left;
        }
        return minv;
    }

    void insert(Line p, double dist) {
        root = insertRec(root, p, dist);
    }

    /* A recursive function to insert a new key in BST */
    Node insertRec(Node root, Line p, double dist) {

        /* If the tree is empty, return a new node */
        if (root == null) {
            root = new Node(p, dist);
            return root;
        }

        /* Otherwise, recur down the tree */
        if (dist <= root.d)
            root.left = insertRec(root.left, p, dist);
        else
            root.right = insertRec(root.right, p, dist);

        /* return the (unchanged) node pointer */
        return root;
    }




    static long area(Point a, Point b, Point c)
    {
        return (long)((b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX()));
    }
    static boolean intersect_1(int a, int b, int c, int d)
    {
        if (a > b){
            int buf = a;
            a = b;
            b = buf;
        }
        if (c > d)
        {
            int buf = c;
            c = d;
            d = buf;
        }
        return Math.max(a, c) <= Math.min(d, b);
    }



    public Line getLeft()
    {
        Node temp = root;
        if (temp == null) return null;
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp.key;
    }
    void inorderRec(Node root) {
        if (root != null) {
            mLines.add(root.key);
            inorderRec(root.left);
            inorderRec(root.right);
        }
    }

    public ArrayList<Line> getLines()
    {
        mLines = new ArrayList<>();
        inorderRec(root);
        return mLines;
    }
}
