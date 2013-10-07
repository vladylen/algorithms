/**
 * Auto Generated Java Class.
 */
public class PercolationStats {
    public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
    {
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            boolean bool1 = percolation.isFull(1, 1);
            boolean bool2 = percolation.isOpen(1, 1);
            boolean bool3 = percolation.percolates();
            percolation.open(1, 1);
            boolean bool4 = percolation.isFull(1, 1);
            boolean bool5 = percolation.isOpen(1, 1);
            boolean bool6 = percolation.percolates();
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
        PercolationStats percolationStats = new PercolationStats(10, 1);
    }
}