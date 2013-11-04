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
        Queue<Point2D> points = new Queue<Point2D>();
        //Queue<Point2D> points = bst2D.range(rect);

        return points;
    }

    public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
    {
        return p;
    }

    private static void main1(String[] args) {
        Point2D p1 = new Point2D(3, 2);
        Point2D p2 = new Point2D(2, 4);
        Point2D p3 = new Point2D(4, 4);
        Point2D p4 = new Point2D(1, 5);
        Point2D p5 = new Point2D(5, 1);
        Point2D p6 = new Point2D(2, 3);

        KdTree tree = new KdTree();

        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        tree.insert(p5);
        tree.insert(p6);

        StdOut.println("\r\nContains");
        StdOut.println(tree.contains(new Point2D(1.5, 1.8)));
        StdOut.println("\r\nContains");
        StdOut.println(tree.contains(new Point2D(2, 3)));

        StdOut.println("\r\nNearest");
        StdOut.println(tree.nearest(new Point2D(1.5, 1.8)));
        StdOut.println("\r\nSize = " + tree.size());

        RectHV rect1 = new RectHV(1.5, 2.5, 2.5, 3.5);
        RectHV rect2 = new RectHV(1.5, 0.5, 5.5, 3.5);

        StdOut.println("\r\nRange");
        for (Point2D point : tree.range(rect1)) {
            StdOut.println(point);
        }
    }

    private static class Node {
        private Point2D p;      // the point
        private Node lb;        // the lb/bottom subtree
        private Node rt;        // the rt/top subtree
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node

        public Node(Point2D p, Node lb, Node rt) {
            this.p = p;
            this.lb = lb;
            this.rt = rt;
        }
    }

    private class BST2D {
        private Node root;             // root of BST
        private int N = 0;
        private static final int DIRECTION_X = 1;
        private static final int DIRECTION_Y = 0;

        public boolean isEmpty() {
            return size() == 0;
        }

        public int size() {
            return N;
        }

        public void put(Point2D p) {
            Node node = new Node(p, null, null);

            if (!contains(p)) {
                N++;
                root = put(root, node, DIRECTION_X);
            }
        }

        private Node put(Node parent, Node element, int directionCurr) {
            if (parent == null) {
                return element;
            }

            int directionNew = (directionCurr == DIRECTION_X) ? DIRECTION_Y : DIRECTION_X;

            int cmp = comparePoints(element.p, parent.p, directionCurr);

            if (cmp < 0) parent.lb = put(parent.lb, element, directionNew);
            else if (cmp > 0) parent.rt = put(parent.rt, element, directionNew);
            else if (element.p.compareTo(parent.p) == 0) return parent;
            else parent.rt = put(parent.rt, element, directionNew);

            return parent;
        }

        private int comparePoints(Point2D p1, Point2D p2, int direction) {
            int cmp;
            double value1;
            double value2;

            if (direction == DIRECTION_X) {
                value1 = p1.x();
                value2 = p2.x();
            } else {
                value1 = p1.y();
                value2 = p2.y();
            }

            if (value1 > value2) {
                cmp = 1;
            } else if (value1 < value2) {
                cmp = -1;
            } else {
                cmp = 0;
            }

            return cmp;
        }

        public boolean contains(Point2D p) {
            return get(p) != null;
        }

        public Node get(Point2D p) {
            return get(root, p, DIRECTION_X);
        }

        private Node get(Node parent, Point2D p, int directionCurr) {
            if (parent == null) return null;

            int directionNew = (directionCurr == DIRECTION_X) ? DIRECTION_Y : DIRECTION_X;

            int cmp = comparePoints(p, parent.p, directionCurr);

            if (cmp < 0) return get(parent.lb, p, directionNew);
            else if (cmp > 0) return get(parent.rt, p, directionNew);
            else if (p.compareTo(parent.p) == 0) return parent;
            else return get(parent.rt, p, directionNew);
        }

        public Queue<Point2D> range(RectHV rect) {
            Queue<Point2D> queue = new Queue<Point2D>();
            return range(root, rect, queue, DIRECTION_X);
        }

        private Queue<Point2D> range(Node parent, RectHV rect, Queue<Point2D> queue, int directionCurr) {
            if (parent == null) return null;

            int directionNew = (directionCurr == DIRECTION_X) ? DIRECTION_Y : DIRECTION_X;

            int cmp = comparePoints(new Point2D(rect.xmin(), rect.ymin()), parent.p, directionCurr);

            if (cmp < 0) return range(parent.lb, rect, queue, directionNew);
            else if (cmp > 0) return range(parent.rt, rect, queue, directionNew);
            else queue.enqueue(parent.p);

            return queue;
        }
    }
}
