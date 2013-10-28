import java.util.Arrays;
import java.util.Iterator;

public class Board {
    private int N;
    private int[][] blocks;

    //OK
    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
    {
        N = blocks.length;
        this.blocks = blocks;
    }

    //OK
    public int dimension()                 // board dimension N
    {
        return N;
    }

    //OK
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

    //OK
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int distance = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] != 0) {
                    distance += distance(i, j, this.blocks[i][j]);
                }
            }
        }

        return distance;
    }

    //OK
    public boolean isGoal()                // is this board the goal board?
    {
        return hamming() == 0;
    }

    //OK
    public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
    {
        int[][] twinBlocks = new int[N][N];
        boolean exchange = false;

        for (int i = 0; i < N; i++) {
            int sequence = 0;

            for (int j = 0; j < N; j++) {
                twinBlocks[i][j] = this.blocks[i][j];

                if (!exchange) {
                    if (twinBlocks[i][j] == 0) {
                        sequence = 0;
                    } else {
                        sequence++;
                    }

                    if (sequence > 1) {
                        int value = twinBlocks[i][j - 1];
                        twinBlocks[i][j - 1] = twinBlocks[i][j];
                        twinBlocks[i][j] = value;
                        exchange = true;
                    }
                }
            }
        }

        return new Board(twinBlocks);
    }

    //OK
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;

        return (this.N == that.N) && (Arrays.deepEquals(this.blocks, that.blocks));
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
                s.append(String.format("%2d ", blocks[i][j]));
                //s.append(String.format("%2d(%2d)(%2d) ", blocks[i][j], position(i, j), distance(i, j, blocks[i][j])));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int distance(int i, int j, int value) {
        return Math.abs(i - getY(value)) + Math.abs(j - getX(value));
    }

    private int position(int i, int j) {
        if (i < N && j < N && i >= 0 && j >= 0) {
            return i * N + j + 1;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private int getX(int position) {
        return (position - 1) % N;
    }

    private int getY(int position) {
        return (position - 1) / N;
    }
}
