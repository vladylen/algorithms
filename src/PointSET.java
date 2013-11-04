public class PointSET {
    private SET<Point2D> set;

    public PointSET()                               // construct an empty set of points
    {
        set = new SET<Point2D>();
    }

    public boolean isEmpty()                        // is the set empty?
    {
        return set.isEmpty();
    }

    public int size()                               // number of points in the set
    {
        return set.size();
    }

    public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
    {
        set.add(p);
    }

    public boolean contains(Point2D p)              // does the set contain the point p?
    {
        return set.contains(p);
    }

    public void draw()                              // draw all of the points to standard draw
    {
        for (Point2D point : set) {
            point.draw();
        }
        StdDraw.show(0);
    }

    public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
    {
        Queue<Point2D> range = new Queue<Point2D>();

        for (Point2D point : set) {
            if (rect.distanceTo(point) == 0) {
                range.enqueue(point);
            }
        }

        return range;
    }

    public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
    {
        Point2D nearest = null;
        double minLength = -1;

        for (Point2D point : set) {
            double length = p.distanceTo(point);

            if (minLength < 0) {
                minLength = length;
                nearest = point;
            } else if (length < minLength) {
                minLength = length;
                nearest = point;
            }
        }

        return nearest;
    }

    private static void main1(String[] args) {
        Point2D p1 = new Point2D(1, 1);
        Point2D p2 = new Point2D(1.3, 1.3);
        Point2D p3 = new Point2D(2, 2);
        Point2D p4 = new Point2D(2.5, 2.5);
        Point2D p5 = new Point2D(3, 3);
        Point2D p6 = new Point2D(2.1, 2.1);

        PointSET set = new PointSET();

        set.insert(p1);
        set.insert(p2);
        set.insert(p3);
        set.insert(p4);
        set.insert(p5);
        set.insert(p6);

        StdOut.println("\r\nNearest");
        StdOut.println(set.nearest(new Point2D(1.5, 1.8)));

        RectHV rect = new RectHV(1.4, 1.4, 3, 3);

        StdOut.println("\r\nRange");
        for (Point2D point : set.range(rect)) {
            StdOut.println(point);
        }
    }
}