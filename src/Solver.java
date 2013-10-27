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

    public static void main(String[] args)  // solve a slider puzzle (given below)
    {
    }
}
