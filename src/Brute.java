import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;

public class Brute {
    public static void main(String[] args) {
        args = new String[1];
        args[0] = "input6.txt";
        draw(args[0]);

        if (args.length > 0) {
            String argFileName = args[0];

            try {
                In in = new In(argFileName);
                int N = in.readInt();
                Point[] Points = new Point[N];

                for (int i = 0; i < N; i++) {
                    Points[i] = new Point(in.readInt(), in.readInt());
                    StdOut.println(Points[i].toString());
                }

                calculation(Points);
            } catch (Exception ex) {
                StdOut.println(ex.toString());
            }
        } else {
            StdOut.println("main() - No arguments.");
        }
        /*
        if (q.slopeTo(w) == q.slopeTo(e) && q.slopeTo(w) == q.slopeTo(r)) {
        }
        */
    }

    private static void calculation(Point[] Points) {
        for (int i = 0; i < Points.length; i++) {
            for (int k = i + 1; k < Points.length; k++) {
                for (int l = k + 1; l < Points.length; l++) {
                    for (int m = l + 1; m < Points.length; m++) {
                        double slope = Points[i].slopeTo(Points[k]);
                        if ((Points[i].slopeTo(Points[l]) == slope) && (Points[i].slopeTo(Points[m]) == slope)) {
                            StdOut.println("quadro");
                            StdOut.println(Points[i].toString());
                            StdOut.println(Points[k].toString());
                            StdOut.println(Points[l].toString());
                            StdOut.println(Points[m].toString());
                        }
                    }
                }
            }
        }
    }

    private static void draw(String filename) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        In in = new In(filename);
        int N = in.readInt();
        Point prevPoint = new Point(0, 0);

        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            prevPoint.drawTo(p);
        }

        // display to screen all at once
        StdDraw.show(0);
    }
}
