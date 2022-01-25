import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        validatePoints(points);
        // points in natural order
        Point[] pointsNO = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsNO);
        // points in slope order
        Point[] pointsSO = Arrays.copyOf(points, points.length);

        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        for (int i = 0; i < pointsNO.length; i++) {
            Point origin = pointsNO[i];
            Point lineBeginning = null;
            Arrays.sort(pointsSO, origin.slopeOrder());
            int collinearCount
                    = 1;
            for (int j = 0; j < pointsSO.length - 1; j++) {
                if (pointsSO[j].slopeTo(origin) == pointsSO[j + 1].slopeTo(origin)) {
                    collinearCount++;
                    if (collinearCount == 2) {
                        lineBeginning = pointsSO[j];
                        collinearCount++;
                    }
                    else if (collinearCount >= 4 && j + 1 == pointsSO.length - 1) {
                        if (lineBeginning.compareTo(origin) > 0) {
                            segmentsList.add(new LineSegment(origin, pointsSO[j + 1]));
                        }
                        collinearCount = 1;
                    }
                }
                else if (collinearCount >= 4) {
                    if (lineBeginning.compareTo(origin) > 0) {
                        segmentsList.add(new LineSegment(origin, pointsSO[j]));
                    }
                    collinearCount = 1;
                }
                else {
                    collinearCount = 1;
                }
            }
        }
        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }

    // store minAndMax rather than line segments then detect duplicates before creating final line segments
    // use the absolute slope so all duplicate segments go from top to bottom rather than only through half of the segment
    // then they'll be easier to dedupe


    public static void main(String[] args) {
        int n = 8;
        int[] xVals = { 0, 1, 2, 3, 5, 6, 7, 8 };
        int[] yVals = { 0, 1, 2, 4, 5, 6, 7, 8 };

        Point[] points = new Point[8];
        for (int i = 0; i < n; i++) {
            int x = xVals[i];
            int y = yVals[i];
            points[i] = new Point(x, y);
        }

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        System.out.println("segments ? " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            System.out.println("segment: " + segment);
            StdOut.println(segment);
        }
    }

    private void validatePoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points must not be null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point must not be null");
            }
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("points must not be duplicates");
                }
            }
        }
    }
}
