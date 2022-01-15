import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


public class FastCollinearPoints {
    private final LineSegment[] segments;
    private final int n;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        validatePoints(points);

        segments = new LineSegment[points.length];
        int count = 0;
        for (Point point : points) {
            Arrays.sort(points, point.slopeOrder());
            double[] pointSlopes = new double[points.length];
            for (int slopeIndex = 0; slopeIndex < points.length; slopeIndex++) {
                pointSlopes[slopeIndex] = point.slopeTo(points[slopeIndex]);
            }

            int j = 0;
            while (j < points.length - 3) {
                if (pointSlopes[j] == pointSlopes[j + 1] && pointSlopes[j + 1] == pointSlopes[j + 2] && pointSlopes[j + 2] == pointSlopes[j + 3]) {
                    int extraPoints = 0;
                    while (pointSlopes[j] == pointSlopes[j + 4 + extraPoints]) {
                        extraPoints++;
                    }
                    segments[count] = new LineSegment(points[j], points[j + 3 + extraPoints]);
                    count++;
                    j += 1 + extraPoints;
                } else {
                    j++;
                }
            }
        }
        n = count;
    }

    // the number of line segments
    public int numberOfSegments() {
        return n;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segmentsCopy = new LineSegment[n];
        System.arraycopy(segments, 0, segmentsCopy, 0, n);
        return segmentsCopy;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            System.out.println("looping");
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            System.out.println("point: " + p);
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private void validatePoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points must not be null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point must not be null");
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == points[j]) {
                    throw new IllegalArgumentException("points must not be duplicates");
                }
            }
        }
    }
}
