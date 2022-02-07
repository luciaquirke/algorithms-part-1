/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {
    private int size = 0;
    private Node root;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (isEmpty()) {
            root = new Node();
            root.p = p;
            // root.rect = new RectHV(0, 0, 1, 1);
        }
        else {
            insert(p, root);
        }
        size++;
    }

    private void insert(Point2D p, Node parent) {
        if (p.x() <= parent.p.x()) {
            if (parent.lb == null) {
                parent.lb = new Node();
                parent.lb.p = p;
                // parent.lb.rect = new RectHV(0, 0, x, 1);
            }
            else {
                insert(p, parent.lb);
            }
        }
        else if (p.x() > parent.p.x()) {
            if (parent.rt == null) {
                parent.rt = new Node();
                parent.rt.p = p;
                // parent.rt.rect = new RectHV(0, 0, x, 1);
            }
            else {
                insert(p, parent.rt);
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        // not implemented
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        // not implemented
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        // not implemented
        return new ArrayList();
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        // not implemented
        return p;
    }

    public static void main(String[] args) {
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }
}
