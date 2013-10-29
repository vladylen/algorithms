import java.util.Comparator;

public class Solver {
    private Board initialBoard;
    private int minSteps;
    private boolean solvable = false;
    private Queue<Board> solution;

    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
        solution = new Queue<Board>();
        initialBoard = initial;
        solving();
    }

    private void solving() {
        MinPQ<Node> mq = new MinPQ<Node>(10, new NodeComparator());

        double max = 100;
        if (initialBoard.dimension() < 10) {
            max = 500;
        } else if (initialBoard.dimension() < 15) {
            max = 5000;
        } else {
            max = 50000;
        }

        int steps = 0;
        Node initialNode = new Node(initialBoard, steps);

        mq.insert(initialNode);
        Node searchNode = mq.delMin();
        solution.enqueue(searchNode.board);

        Board previousBoard = null;
        boolean goal = initialNode.board.isGoal();

        /** /
        StdOut.println("steps = " + steps);
        StdOut.println(searchNode.board);
        /**/

        while (!goal) {
            steps++;
            for (Board neighbor : searchNode.board.neighbors()) {
                if (previousBoard == null || !previousBoard.equals(neighbor)) {
                    Node currentNode = new Node(neighbor, steps);
                    mq.insert(currentNode);
                }
            }

            previousBoard = searchNode.board;

            do {
                searchNode = mq.delMin();
            } while (searchNode.moves != steps);

            solution.enqueue(searchNode.board);
            goal = searchNode.board.isGoal();

            /** /
            StdOut.println("steps = " + steps);
            StdOut.println(searchNode.board);
            /**/

            if ((double) steps > max) break;
        }

        if (goal) {
            solvable = true;
            minSteps = steps;
        } else {
            minSteps = 0;
        }
    }

    public boolean isSolvable()             // is the initial board solvable?
    {
        return solvable;
    }

    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    {
        if (isSolvable()) {
            return minSteps;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    {
        if (isSolvable()) {
            return solution;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        boolean debug = true;

        if (debug) {
            args = new String[1];
            args[0] = "8puzzle/puzzle14.txt";
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

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class Node {
        int priority;
        int moves;
        int manhattan;
        int hamming;
        Board board;

        public Node(Board board, int moves) {
            this.board = board;
            manhattan = board.manhattan();
            hamming = board.hamming();
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

    private static int factorial(int n) {
        int fact = 1; // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
}
