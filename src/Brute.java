import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        /*
        args = new String[1];
        args[0] = "input8.txt";
        */

        if (args.length > 0) {
            String argFileName = args[0];

            try {
                In in = new In(argFileName);
                int N = in.readInt();
                Point[] points = new Point[N];

                for (int i = 0; i < N; i++) {
                    points[i] = new Point(in.readInt(), in.readInt());
                }

                drawPoints(points);
                calculationBrute(points);
            } catch (Exception ex) {
                StdOut.println(ex.toString());
            }
        } else {
            StdOut.println("main() - No arguments.");
        }
    }

    private static void calculationBrute(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int k = i + 1; k < points.length; k++) {
                for (int l = k + 1; l < points.length; l++) {
                    for (int m = l + 1; m < points.length; m++) {
                        double slope = points[i].slopeTo(points[k]);
                        if ((points[i].slopeTo(points[l]) == slope) && (points[i].slopeTo(points[m]) == slope)) {
                            Point[] sequence = new Point[4];
                            sequence[0] = points[i];
                            sequence[1] = points[k];
                            sequence[2] = points[l];
                            sequence[3] = points[m];
                            Arrays.sort(sequence);
                            StdOut.println(getSequence(sequence));
                            drawSequence(sequence);
                        }
                    }
                }
            }
        }
    }

    private static String getSequence(Point[] sequence) {
        String str = "";

        for (int i = 0; i < sequence.length; i++) {
            str += sequence[i].toString();

            if (i != (sequence.length - 1)) {
                str += " -> ";
            }
        }

        return str;
    }

    private static void drawPoints(Point[] points) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        for (int i = 0; i < points.length; i++) {
            /*
            for (int k = 0; k < 5; k++) {
                for (int l = 0; l < 5; l++) {
                    Point point = new Point(points[i].getX() - 2 + k, points[i].getY() + 2 - l);
                    point.draw();
                }
            }
            */
            points[i].draw();
        }
        StdDraw.show(0);
    }

    private static void drawSequence(Point[] points) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        points[0].drawTo(points[points.length - 1]);
        StdDraw.show(0);
    }
}
