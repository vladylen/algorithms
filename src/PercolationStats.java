public class PercolationStats {
    private double mean = 0;
    private double deviation = 0;
    private double confidenceLo = 0;
    private double confidenceHi = 0;

    public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
    {
        int[] counts = new int[T];
        double[] ps = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, N + 1), StdRandom.uniform(1, N + 1));
            }

            for (int k = 0; k < percolation.grid.length; k++) {
                if (percolation.grid[k] > 0) {
                    counts[i]++;
                }
            }

            ps[i] = (double) counts[i] / ((double) N * (double) N);
            mean += ps[i] / (double) T;

            //StdOut.println(i + ". count=" + counts[i] + " and p=" + ps[i]);
        }

        deviation = StdStats.stddev(ps);
        confidenceLo = mean - 1.96 * deviation / Math.sqrt((double) T);
        confidenceHi = mean + 1.96 * deviation / Math.sqrt((double) T);
    }

    public double mean()                     // sample mean of percolation threshold
    {
        return mean;
    }

    public double stddev()                   // sample standard deviation of percolation threshold
    {
        return deviation;
    }

    public double confidenceLo()             // returns lower bound of the 95% confidence interval
    {
        return confidenceLo;
    }

    public double confidenceHi()             // returns upper bound of the 95% confidence interval
    {
        return confidenceHi;
    }
//
//    public void example(int N) {
//        Percolation percolation = new Percolation(N);
//        boolean bool1 = percolation.isFull(2, 2);
//        boolean bool2 = percolation.isOpen(2, 2);
//        boolean bool3 = percolation.percolates();
//
//        percolation.open(1, 2);
//        percolation.open(2, 2);
//        percolation.open(2, 3);
//        percolation.open(3, 3);
//        boolean bool4 = percolation.isFull(2, 2);
//        boolean bool5 = percolation.isOpen(2, 2);
//        boolean bool6 = percolation.percolates();
//        int[] grid2 = percolation.grid;
//
//        percolation.open(4, 3);
//        percolation.open(4, 4);
//        percolation.open(5, 4);
//        boolean bool7 = percolation.isFull(2, 2);
//        boolean bool8 = percolation.isOpen(2, 2);
//        boolean bool9 = percolation.percolates();
//        int[] grid3 = percolation.grid;
//    }

    public static void main(String[] args)   // test client, described below
    {
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats percolationStats = new PercolationStats(N, T);

        StdOut.println("mean = " + percolationStats.mean());
        StdOut.println("deviation = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }
}