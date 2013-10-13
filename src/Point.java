/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER;

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
        SLOPE_ORDER = new ByName(x, y);
    }

    private class ByName implements Comparator<Point> {
        int x;
        int y;

        public ByName(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int compare(Point v, Point w) {
            if (slopeTo(v.x, v.y) < slopeTo(w.x, w.y)) {
                return -1;
            } else if (slopeTo(v.x, v.y) == slopeTo(w.x, w.y)) {
                return 0;
            } else {
                return 1;
            }
        }
        // slope between this point and that point
        private double slopeTo(int x, int y) {
            return (this.y - y) / (this.x - x);
        }
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        return (that.y - this.y) / (that.x - this.y);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if ((this.y < that.y) || (this.y == that.y && this.x < that.x)) {
            return -1;
        } else if (this.y == that.y && this.x == that.x) {
            return 0;
        } else {
            return 1;
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        Point q = new Point(0, 0);
        Point w = new Point(6, 4);
        Point e = new Point(3, 3);
        Point r = new Point(10, 2);

        q.draw();
        w.draw();
        e.draw();
        r.draw();

        q.drawTo(w);
        q.drawTo(e);
        q.drawTo(r);
    }
}
