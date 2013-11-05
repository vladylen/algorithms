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
        return bst2D.range(rect);
    }

    public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
    {
        return bst2D.nearest(p);
    }

    public static void main(String[] args) {
        /**/
        args = new String[1];
        args[0] = "circle10000-.txt";
        /**/

        int N = 0;
        KdTree tree = new KdTree();
        PointSET set = new PointSET();

        if (args.length > 0) {
            String argFileName = args[0];

            try {
                In in = new In(argFileName);

                while (!in.isEmpty()) {
                    N++;
                    double x = in.readDouble();
                    double y = in.readDouble();

                    tree.insert(new Point2D(x, y));
                    set.insert(new Point2D(x, y));
                }
            } catch (Exception ex) {
                StdOut.println(ex.toString());
            }
        } else {
            StdOut.println("main() - No arguments.");
        }

        int errors = checkErrors(tree, set);
        StdOut.println("errors = " + errors);
    }

    private static int checkErrors(KdTree tree, PointSET set) {
        int errors = 0;
        Point2D point = new Point2D(0.44299505287076113, 0.38603414793313195);

        Point2D nearestTree = tree.nearest(point);
        Point2D nearestSet = set.nearest(point);

        if (!nearestSet.equals(nearestTree)) {
            errors++;
            StdOut.println("\r\n");
            StdOut.println("point = " + point);
            StdOut.println("nearest tree = " + nearestTree + " distance = " + nearestSet.distanceTo(point));
            StdOut.println("nearest set = " + nearestSet + " distance = " + nearestTree.distanceTo(point));
        }
        return errors;
    }
}





/*
Point2D p1 = new Point2D(5, 4);
Point2D p2 = new Point2D(6, 3);
Point2D p3 = new Point2D(7, 6);

Point2D p4 = new Point2D(4, 2);
Point2D p5 = new Point2D(3, 3);
Point2D p6 = new Point2D(2, 6);
Point2D p7 = new Point2D(4.5, 3.5);
Point2D p8 = new Point2D(4.75, 5);
Point2D p9 = new Point2D(4.875, 7);

KdTree tree = new KdTree();

tree.insert(p1);
tree.insert(p2);
tree.insert(p3);
tree.insert(p4);
tree.insert(p5);
tree.insert(p6);
tree.insert(p7);
tree.insert(p8);
tree.insert(p9);

StdOut.println(tree.nearest(new Point2D(2, 5)));
*/
