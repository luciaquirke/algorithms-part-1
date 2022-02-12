/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

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
        Node parent = null;
        Node node = root;
        int level = 0;

        // find the parent of our new point
        while (node != null) {
            parent = node;
            double pointValue = level == 0 ? p.x() : p.y();
            double nodeValue = level == 0 ? node.p.x() : node.p.y();

            if (pointValue < nodeValue) {
                node = node.lb;
            }
            else {
                // the data structure represents a set of points so duplicates aren't added
                if (p.x() == node.p.x() && p.y() == node.p.y()) {
                    return;
                }
                node = node.rt;

            }
            level = (level + 1) % 2;
        }

        if (parent == null) {
            this.root = new Node();
            this.root.p = p;
            this.root.rect = new RectHV(0, 0, 1, 1);
        }
        else {
            // insert the new point under its parent
            // check if level == 1 here because the level gets updated one time after the node becomes null
            // this only works because it's a 2d tree so going backwards is the same as going forwards
            double pointValue = level == 1 ? p.x() : p.y();
            double parentValue = level == 1 ? parent.p.x() : parent.p.y();

            if (pointValue < parentValue) {
                parent.lb = new Node();
                parent.lb.p = p;

                // rectangle is defined by the splitting point of the parent
                if (level == 0) {
                    parent.lb.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                                parent.rect.xmax(),
                                                parent.p.y());
                    // System.out.println("adding lb 0 rect");
                    // System.out.println(parent.lb.rect);
                }

                // first proper insertion at level 1 - based on parent at level 0, therefore by x dimension
                if (level == 1) {
                    parent.lb.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                                parent.p.x(),
                                                parent.rect.ymax());
                    // System.out.println("adding lb 1 rect");
                    // System.out.println(parent.lb.rect);
                }
            }
            else {
                parent.rt = new Node();
                parent.rt.p = p;

                if (level == 0) {
                    parent.rt.rect = new RectHV(parent.rect.xmin(), parent.p.y(),
                                                parent.rect.xmax(), parent.rect.ymax());
                    // System.out.println("adding rt 0 rect");
                    // System.out.println(parent.rt.rect);
                }

                if (level == 1) {
                    parent.rt.rect = new RectHV(parent.p.x(), parent.rect.ymin(),
                                                parent.rect.xmax(), parent.rect.ymax());
                    // System.out.println("adding rt 1 rect");
                    // System.out.println(parent.rt.rect);
                }

            }
            // System.out.println(
            //         "parent rect: " + parent.rect + " p: " + parent.p.x() + ", " + parent.p.y());
        }

        size++;
    }

    // private void recursiveInsert(Point2D p, Node current, Node parent, int level, String side) {
    //     if (current == null) {
    //         Node newNode = new Node();
    //         newNode.p = p;
    //         if (side.equals("lb")) {
    //             parent.lb = newNode;
    //
    //             if (level == 0)
    //                 parent.lb.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), p.x(),
    //                                             parent.rect.ymax());
    //             if (level == 1) parent.lb.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
    //                                                         parent.rect.xmax(),
    //                                                         p.y());
    //
    //         }
    //         if (side.equals("rt")) {
    //             parent.rt = newNode;
    //             if (level == 0) parent.rt.rect = new RectHV(p.x(), parent.rect.ymin(),
    //                                                         parent.rect.xmax(), parent.rect.ymax());
    //             if (level == 1) parent.rt.rect = new RectHV(parent.rect.xmin(), p.y(),
    //                                                         parent.rect.xmax(), parent.rect.ymax());
    //
    //         }
    //         return;
    //     }
    //
    //     double pointValue = level == 0 ? p.x() : p.y();
    //     double currentValue = level == 0 ? current.p.x() : current.p.y();
    //
    //     if (pointValue < currentValue) {
    //         recursiveInsert(p, current.lb, current, (level + 1) % 2, "lb");
    //     }
    //     // point already exists
    //     else if (pointValue == currentValue) {
    //         return;
    //     }
    //     else if (pointValue >= currentValue) {
    //         recursiveInsert(p, current.rt, current, (level + 1) % 2, "rt");
    //     }
    // }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node node = root;
        int level = 0;
        while (node != null) {
            // use parent rect instead of level eventually
            double pointValue = level == 0 ? p.x() : p.y();
            double nodeValue = level == 0 ? node.p.x() : node.p.y();

            if (pointValue < nodeValue) {
                // System.out.println("changing node to lb");
                node = node.lb;
                level = (level + 1) % 2;
            }
            else {
                // System.out.println("changing node to rt");
                if (p.x() == node.p.x() && p.y() == node.p.y()) {
                    return true;
                }
                node = node.rt;
                level = (level + 1) % 2;
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        if (root == null) return;
        draw(root, 0);
    }

    private void draw(Node node, int level) {
        if (node == null) return;

        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(node.p.x(), node.p.y());

        StdDraw.setPenRadius(0.005);
        if (level == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        if (level == 1) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }

        draw(node.lb, (level + 1) % 2);
        draw(node.rt, (level + 1) % 2);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        return range(rect, root);
    }

    private ArrayList<Point2D> range(RectHV rect, Node current) {
        ArrayList<Point2D> rangePoints = new ArrayList<>();

        if (current != null && current.rect.intersects(rect)) {

            // check if the point is in range
            if (rect.contains(current.p)) rangePoints.add(current.p);
            rangePoints.addAll(range(rect, current.lb));
            rangePoints.addAll(range(rect, current.rt));
        }

        return rangePoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (root == null) return null;
        Point2D nearest = root.p;
        nearest(p, root, nearest, 0);
        return nearest;
    }

    // query point = p
    // splitting line = ?
    // search the subtree that is on the same side of the splitting line as the query point first
    private void nearest(Point2D p, Node current, Point2D best, int level) {
        if (current != null && current.rect.distanceSquaredTo(p) <= p.distanceSquaredTo(best)) {
            if (p.distanceSquaredTo(current.p) < p.distanceSquaredTo(best)) {
                best = current.p;
            }

            if (current.lb != null && current.lb.rect.contains(p)) {
                nearest(p, current.lb, best, (level + 1) % 2);
                nearest(p, current.rt, best, (level + 1) % 2);
            }
            else {
                nearest(p, current.rt, best, (level + 1) % 2);
                nearest(p, current.lb, best, (level + 1) % 2);
            }

        }
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.8, 0.7));
        kdTree.insert(new Point2D(0.1, 0.9));
        kdTree.insert(new Point2D(0.4, 0.6));
        kdTree.insert(new Point2D(0.3, 0.1));
        assert !kdTree.contains(new Point2D(0.5, 0.1));
        assert kdTree.contains(new Point2D(0.4, 0.6));
        kdTree.draw();
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }
}
