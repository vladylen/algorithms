public class KdTree {
    private BST2D bst2D;

    public KdTree()                               // construct an empty set of points
    {
        bst2D = new BST2D();
    }

    public boolean isEmpty()                        // is the set empty?
    {
        return bst2D.isEmpty();
    }

    public int size()                               // number of points in the set
    {
        return bst2D.size();
    }

    public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
    {
        bst2D.put(p);
    }

    public boolean contains(Point2D p)              // does the set contain the point p?
    {
        return bst2D.contains(p);
    }

    public void draw()                              // draw all of the points to standard draw
    {
        /*
        for (Point2D point : bst2D) {
            point.draw();
        }
        StdDraw.show(0);
        */
    }

    public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
    {
        //Queue<Point2D> points = new Queue<Point2D>();
        Queue<Point2D> points = bst2D.range(rect);

        return points;
    }

    public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
    {
        return p;
    }

    public static void main(String[] args) {
        Point2D p1 = new Point2D(3, 2);
        Point2D p2 = new Point2D(2, 4);
        Point2D p3 = new Point2D(4, 4);
        Point2D p4 = new Point2D(1, 5);
        Point2D p5 = new Point2D(5, 1);
        Point2D p6 = new Point2D(2, 3);
        //Point2D p7 = new Point2D(2, 3.5);

        KdTree tree = new KdTree();

        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        tree.insert(p5);
        tree.insert(p6);
        //tree.insert(p7);

        StdOut.println("\r\nContains");
        StdOut.println(tree.contains(new Point2D(1.5, 1.8)));
        StdOut.println("\r\nContains");
        StdOut.println(tree.contains(new Point2D(2, 3)));

        StdOut.println("\r\nNearest");
        StdOut.println(tree.nearest(new Point2D(1.5, 1.8)));
        StdOut.println("\r\nSize = " + tree.size());

        RectHV rect1 = new RectHV(1.5, 2.5, 2.5, 3.5);
        RectHV rect2 = new RectHV(1.5, 0.5, 5.5, 3.5);
        RectHV rect3 = new RectHV(4, 0, 5, 1);

        StdOut.println("\r\nRange");
        for (Point2D point : tree.range(rect1)) {
            StdOut.println(point);
        }
        StdOut.println("\r\nRange");
        for (Point2D point : tree.range(rect2)) {
            StdOut.println(point);
        }
        StdOut.println("\r\nRange");
        for (Point2D point : tree.range(rect3)) {
            StdOut.println(point);
        }
    }
}
