import java.util.Comparator;

public class BruteCollinearPoints {
    private final LineSegment[] segments;
    private final int n;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

        // permutation without repetition
        int count = 0;
        segments = new LineSegment[points.length];

        double[][][] pointCompares = new double[points.length][points.length][points.length];
        double[][] pointCompareTos = new double[points.length][points.length];
        for (int i = 0; i < points.length; i++) {
            Comparator<Point> slopeComparator = points[i].slopeOrder();
            for (int j = 0; j < points.length; j++) {
                pointCompareTos[i][j] = points[i].compareTo(points[j]);
                for (int k = 0; k < points.length; k++) {
                    pointCompares[i][j][k] = slopeComparator.compare(points[j], points[k]);
                }
            }
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i == j) {
                    continue;
                }
                for (int k = 0; k < points.length; k++) {
                    if (i == k || j == k) {
                        continue;
                    }
                    for (int m = 0; m < points.length; m++) {
                        if (i == m || j == m || k == m) {
                            continue;
                        }
                        // points in ascending order
                        if (pointCompareTos[i][j] > 0 && pointCompareTos[j][k] > 0
                                && pointCompareTos[k][m] > 0) {
                            if (pointCompares[i][j][k] == 0 && pointCompares[i][k][m] == 0) {
                                segments[count] = new LineSegment(points[i], points[m]);
                                count++;
                            }
                        }
                    }
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
}
