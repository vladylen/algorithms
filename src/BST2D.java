public class BST2D {
    private Node root;             // root of BST
    private int N = 1;
    private static final int DIRECTION_X = 1;
    private static final int DIRECTION_Y = 0;

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

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return N;
    }

    public void put(Point2D p) {
        Node node = new Node(p, null, null);

        root = put(root, node, DIRECTION_X);
    }

    private Node put(Node parent, Node element, int directionCurr) {
        if (parent == null) {
            N++;
            return element;
        }

        int directionNew = (directionCurr == DIRECTION_X) ? DIRECTION_Y : DIRECTION_X;

        int cmp = comparePoints(element.p, parent.p, directionCurr);

        if (cmp < 0) parent.lb = put(parent.lb, element, directionNew);
        else if (cmp > 0) parent.rt = put(parent.rt, element, directionNew);
        else N--;

        N++;

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
        else return parent;
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
