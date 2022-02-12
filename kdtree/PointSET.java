/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET {
    private final SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNull(p);
        set.add(p);

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNull(p);
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D point : set) {
            StdDraw.point(point.x(), point.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNull(rect);

        ArrayList<Point2D> pointList = new ArrayList<>();

        for (Point2D point : set) {
            if (point.x() >= rect.xmin() && point.x() <= rect.xmax() && point.y() >= rect.ymin()
                    && point.y() <= rect.ymax()) {
                pointList.add(point);
            }
        }

        return pointList;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNull(p);
        if (set.isEmpty()) return null;

        Point2D nearest = set.min();
        for (Point2D point : set) {
            if (point.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
                nearest = point;
            }
        }
        return nearest;
    }

    // // unit testing of the methods (optional)
    // public static void main(String[] args) {
    // }

    private void checkNull(Object arg) {
        if (arg == null) {
            throw new IllegalArgumentException("arg cannot be null");
        }
    }
}
