import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {
    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
    }

    public boolean isSolvable()             // is the initial board solvable?
    {
        return true;
    }

    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    {
        return 1;
    }

    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return null;
        }
    }

    public static void main(String[] args) {
        /** /
        args = new String[1];
        args[0] = "puzzle04.txt";
        /**/

        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        /** /
        StdOut.println(initial);
        StdOut.println("dimension = " + initial.dimension());
        StdOut.println("hamming = " + initial.hamming());
        StdOut.println("manhattan = " + initial.manhattan());
        StdOut.println("isGoal = " + initial.isGoal());
        StdOut.println("twin = " + initial.twin());
        StdOut.println("equals = " + initial.equals(initial1));
        /**/

        /**/
         // print solution to standard output
         if (!solver.isSolvable())
         StdOut.println("No solution possible");
         else {
         StdOut.println("Minimum number of moves = " + solver.moves());
         for (Board board : solver.solution())
         StdOut.println(board);
         }
         /**/

    }
}
