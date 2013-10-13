import java.util.Comparator;

public class Brute {
    public static void main(String[] args) {
        /*Point q = new Point(0, 0);
        Point w = new Point(6, 4);
        Point e = new Point(3, 3);
        Point r = new Point(10, 2);
        */
        Point q = new Point(0, 0);
        Point w = new Point(1, 1);
        Point e = new Point(3, 3);
        Point r = new Point(10, 10);

        q.draw();
        w.draw();
        e.draw();
        r.draw();

        q.drawTo(w);
        w.drawTo(e);
        e.drawTo(r);
        r.drawTo(q);

        if (q.slopeTo(w) == q.slopeTo(e) && q.slopeTo(w) == q.slopeTo(r)) {
            StdOut.println("Yes!");
        } else {
            StdOut.println("Nop!");
        }
    }
}
