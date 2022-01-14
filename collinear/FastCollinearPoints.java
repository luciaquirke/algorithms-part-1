import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;
    private int n = 0;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points must not be null");
        }
        segments = new LineSegment[points.length];

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == points[j]) {
                    throw new IllegalArgumentException("points must not be duplicates");
                }
            }
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("point must not be null");
            }

            // do not include overlapping segments (find segments even if they're greater than 4)
            Arrays.sort(points, point.slopeOrder());
            int j = 0;
            while (j < points.length - 3) {
                if (point.slopeTo(points[j]) == point.slopeTo(points[j + 1]) && point.slopeTo(points[j + 1]) == point.slopeTo(points[j + 2]) && point.slopeTo(points[j + 2]) == point.slopeTo(points[j + 3])) {
                    segments[n] = new LineSegment(points[j], points[j + 3]);
                    n++;
                    j += 1; // += amount above we found a line segment;
                } else {
                    j++;
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

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
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
}
