public class PercolationStats {
    public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
    {
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            boolean bool1 = percolation.isFull(1, 1);
            boolean bool2 = percolation.isOpen(1, 1);
            boolean bool3 = percolation.percolates();

            percolation.open(1, 2);
            percolation.open(2, 2);
            percolation.open(2, 3);
            percolation.open(3, 3);
            boolean bool4 = percolation.isFull(1, 1);
            boolean bool5 = percolation.isOpen(1, 1);
            boolean bool6 = percolation.percolates();
            int[] grid2 = percolation.grid;

            percolation.open(4, 3);
            percolation.open(4, 4);
            percolation.open(5, 4);
            boolean bool7 = percolation.isFull(1, 1);
            boolean bool8 = percolation.isOpen(1, 1);
            boolean bool9 = percolation.percolates();
            int[] grid3 = percolation.grid;
        }
    }

    public double mean()                     // sample mean of percolation threshold
    {
        return 1.1;
    }

    public double stddev()                   // sample standard deviation of percolation threshold
    {
        return 1.1;
    }

    public double confidenceLo()             // returns lower bound of the 95% confidence interval
    {
        return 1.1;
    }

    public double confidenceHi()             // returns upper bound of the 95% confidence interval
    {
        return 1.1;
    }

    public static void main(String[] args)   // test client, described below
    {
        PercolationStats percolationStats = new PercolationStats(5, 1);
    }
}