import java.util.Arrays;

public class Fast {
    private static int drawCount = 0;

    public static void main(String[] args) {
        /** /
        args = new String[1];
        args[0] = "input80_my.txt";
        /**/

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
                calculationFast(points);
                StdDraw.show(0);
            } catch (Exception ex) {
                StdOut.println(ex.toString());
            }
        } else {
            StdOut.println("main() - No arguments.");
        }
    }

    private static void calculationFast(Point[] points) {
        String[] uniqueSequence = new String[points.length * 7];
        int uniqueCount = 0;

        for (int i = 0; i < points.length; i++) {
            uniqueSequence[i] = "";
        }

        for (int i = 0; i < points.length; i++) {
            Point pointOriginal = points[i];
            Point[] sequence = new Point[points.length];
            Point[] pointsSorted = Arrays.copyOf(points, points.length);
            sequence[0] = pointOriginal;
            int length = 1;
            double slopeOld = 0;

            Arrays.sort(pointsSorted, pointOriginal.SLOPE_ORDER);

            /** /
             StdOut.println(pointOriginal);
             /**/

            for (int l = 0; l < pointsSorted.length; l++) {
                if (pointOriginal != pointsSorted[l]) {
                    double slopeNew = pointOriginal.slopeTo(pointsSorted[l]);
                    if (slopeNew == slopeOld) {
                        //Add element to the sequence
                        sequence[length] = pointsSorted[l];
                        length++;
                    } else if (length >= 4) {
                        //End of the sequence
                        Point[] result;
                        result = Arrays.copyOf(sequence, length);
                        Arrays.sort(result);
                        String str = getSequence(result);
                        boolean key = Arrays.asList(uniqueSequence).contains(str);
                        if (!key) {
                            uniqueSequence[uniqueCount] = str;
                            uniqueCount++;
                            StdOut.println(str);
                            drawSequence(result);
                        }

                        //Start new sequence
                        sequence = new Point[points.length];
                        sequence[0] = pointOriginal;
                        //Begin new sequence
                        sequence[1] = pointsSorted[l];
                        length = 2;
                        slopeOld = slopeNew;
                    } else {
                        //Begin new sequence
                        sequence[1] = pointsSorted[l];
                        length = 2;
                        slopeOld = slopeNew;
                    }
                    /** /
                     StdOut.println(pointsSorted[l].toString() + " " + pointOriginal.slopeTo(pointsSorted[l]));
                     /**/
                }
            }

            if (length >= 4) {
                //End of the sequence
                Point[] result;
                result = Arrays.copyOf(sequence, length);
                Arrays.sort(result);
                String str = getSequence(result);
                boolean key = Arrays.asList(uniqueSequence).contains(str);
                if (!key) {
                    uniqueSequence[uniqueCount] = str;
                    uniqueCount++;
                    StdOut.println(str);
                    drawSequence(result);
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
    }

    private static void drawSequence(Point[] points) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        points[0].drawTo(points[points.length - 1]);
        drawCount++;
    }
}
