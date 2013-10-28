import java.util.Iterator;

public class Board {
    private int N;
    private int emptyPosition;
    private int[][] blocks;

    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
    {
        N = blocks.length;
        this.blocks = blocks;
    }

    // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension N
    {
        return N;
    }

    public int hamming()                   // number of blocks out of place
    {
        int count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] != 0) {
                    if (position(i, j) != this.blocks[i][j]) {
                        count++;
                    }
                }
            }
        }

        return count;
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

    public Iterable<Board> neighbors()       // sequence of boards in a shortest solution; null if no solution
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return null;
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N).append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j], position(i,j)));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int position(int i, int j) {
        if (i < N && j < N && i >= 0 && j >= 0) {
            return i * N + j + 1;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
