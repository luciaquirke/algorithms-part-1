import java.util.Comparator;

public class BruteCollinearPoints {
    private LineSegment[] segments = {};
    private int n = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();
        }
        // sort points and discard duplicates?

        // permutation without repetition
        for (int i = 0; i < points.length; i++) { // -3 to avoid out of bounds errors in successive loops which go 3 above ?
            for (int j = 0; j < points.length; j++) {
                if (j == i) {
                    continue;
                }
                for (int k = 0; k < points.length; k++) {
                    if (i == k || j == k) {
                        continue;
                    }
                    for (int l = 0; l < points.length; l++) {
                        if (i == l || i == j) {
                            continue;
                        }
                        // points in ascending order
                        if (points[i].compareTo(points[j]) > 0 && points[j].compareTo(points[k]) > 0 && points[k].compareTo(points[l]) > 0) {
                            Comparator<Point> slopeComparator = points[i].slopeOrder();
                            if (slopeComparator.compare(points[j], points[k]) == 0 && slopeComparator.compare(points[k], points[l]) == 0) {
                                segments[n] = new LineSegment(points[i], points[l]);
                                n++;
                            }
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return n;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }
}
