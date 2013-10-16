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

    /*
    //TODO remove
    public int getY() {
        return y;
    }

    //TODO remove
    public int getX() {
        return x;
    }
    */

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
        SLOPE_ORDER = new BySlope(x, y);
    }

    private class BySlope implements Comparator<Point> {
        int x;
        int y;

        public BySlope(int x, int y) {
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
            double res;
            double dy = this.y - y;
            double dx = this.x - x;

            if (dy == 0) {
                res = 0;
            } else if (dx == 0) {
                res = 9999999.9999;
            } else {
                res = dy / dx;
            }

            return res;
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
        double res;
        double dy = that.y - this.y;
        double dx = that.x - this.x;

        if (dy == 0) {
            res = 0;
        } else if (dx == 0) {
            res = 9999999.9999;
        } else {
            res = dy / dx;
        }

        return res;
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
    }
}
