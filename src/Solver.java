import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    private Board initialBoard;
    private int steps;
    private boolean solvable = false;
    private Board[] solution;

    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
        initialBoard = initial;
        solving();
    }

    private void solving() {
        MinPQ<Node> mq = new MinPQ<Node>(10, new NodeComparator());

        steps = 0;
        Node initialNode = new Node(initialBoard, steps);

        mq.insert(initialNode);
        Node searchNode = mq.delMin();

        Board previousBoard = null;
        boolean goal = initialNode.board.isGoal();

        StdOut.println("priority=" + searchNode.getPriority());
        StdOut.println(searchNode.board);

        while (!goal) {
            steps++;
            for (Board neighbor : searchNode.board.neighbors()) {
                if (previousBoard == null || !previousBoard.equals(neighbor)) {
                    Node currentNode = new Node(neighbor, steps);
                    mq.insert(currentNode);
                }
            }

            previousBoard = searchNode.board;

            searchNode = mq.delMin();
            goal = searchNode.board.isGoal();

            StdOut.println("priority=" + searchNode.getPriority());
            StdOut.println(searchNode.board);
        }

        if (goal) solvable = true;

        /*
        StdOut.println("********MinPQ********");
        for (Node element : mq) {
            StdOut.println("priority=" + element.getPriority());
            StdOut.println(element.board);
        }
        */
    }

    public boolean isSolvable()             // is the initial board solvable?
    {
        return solvable;
    }

    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    {
        return steps;
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
        boolean debug = true;

        if (debug) {
            args = new String[1];
            args[0] = "puzzle07.txt";
        }

        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        if (!debug) {
            // print solution to standard output
            if (!solver.isSolvable())
                StdOut.println("No solution possible");
            else {
                StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution())
                    StdOut.println(board);
            }
        }

        /*
        if (debug) {
            StdOut.println(initial);
            StdOut.println("dimension = " + initial.dimension());
            StdOut.println("hamming = " + initial.hamming());
            StdOut.println("manhattan = " + initial.manhattan());
            StdOut.println("isGoal = " + initial.isGoal());
            StdOut.println("twin = " + initial.twin());
            StdOut.println("equals = " + initial.equals(initial));
            StdOut.println("neighbors = ");
            for (Board board : initial.neighbors()) {
                StdOut.println(board);
            }
        }
        */
    }

    private class Node {
        int priority;
        int moves;
        int manhattan;
        Board board;

        public Node(Board board, int moves) {
            this.board = board;
            manhattan = board.manhattan();
            this.moves += moves;
            priority = getPriority();
        }

        private int getPriority() {
            return manhattan + moves;
        }
    }

    private class NodeComparator implements Comparator<Node> {
        public int compare(Node node1, Node node2) {
            if (node1.manhattan > node2.manhattan) {
                return 1;
            } else if (node1.manhattan < node2.manhattan) {
                return -1;
            }

            return 0;
        }
    }
}
