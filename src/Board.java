import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
    private int N;

    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
    {
        N = blocks.length;
    }

    // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension N
    {
        return N;
    }

    public int hamming()                   // number of blocks out of place
    {
        return 1;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return 1;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        return true;
    }

    public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
    {
        int[][] board = new int[N][N];
        return new Board(board);
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        return true;
    }

    public String toString()               // string representation of the board (in the output format specified below)
    {
        return "";
    }

    public Iterable<Board> neighbors()       // sequence of boards in a shortest solution; null if no solution
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return null;
        }
    }
}
