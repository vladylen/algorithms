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
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point2D point : set) {
            point.draw();
        }
        StdDraw.show(0);
    }

    public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
    {
        return set;
    }

    public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
    {
        return p;
    }
}