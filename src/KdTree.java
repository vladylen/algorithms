public class KdTree {
    private Queue<Point2D> solution;

    public KdTree()                               // construct an empty set of points
    {
    }

    public boolean isEmpty()                        // is the set empty?
    {
        return true;
    }

    public int size()                               // number of points in the set
    {
        return 1;
    }

    public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
    {
    }

    public boolean contains(Point2D p)              // does the set contain the point p?
    {
        return true;
    }

    public void draw()                              // draw all of the points to standard draw
    {
    }

    public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
    {
        return solution;
    }

    public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
    {
        return p;
    }
}