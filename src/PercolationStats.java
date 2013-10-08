public class PercolationStats {
    private double mean = 0;
    private double deviation = 0;
    private double confidenceLo = 0;
    private double confidenceHi = 0;

    public PercolationStats(int N, int T)   // perform T independent computational experiments on an N-by-N grid
    {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        int[] counts = new int[T];
        double[] ps = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            int[] grid = new int[N * N + 2];
            while (!percolation.percolates()) {

                int p = StdRandom.uniform(1, N + 1);
                int q = StdRandom.uniform(1, N + 1);
                grid[position(p, q, N)] = 1;
                percolation.open(p, q);
            }

            for (int k = 0; k < grid.length; k++) {
                if (grid[k] > 0) {
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

    private int position(int i, int j, int count) {
        i--;
        j--;
        if (i < count && j < count && i >= 0 && j >= 0) {
            return i * count + j + 1;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

//    public static void example(int N) {
//        Percolation percolation = new Percolation(N);
//        percolation.open(4, 1);
//        percolation.open(3, 1);
//        percolation.open(2, 1);
//        percolation.open(1, 1);
//        percolation.open(1, 4);
//        percolation.open(2, 4);
//        percolation.open(4, 4);
//        percolation.open(3, 4);
//        boolean bool1 = percolation.isFull(4, 4);
//        boolean bool2 = percolation.isOpen(4, 4);
//        boolean bool3 = percolation.percolates();
//    }

    public static void main(String[] args)   // test client, described below
    {
        int N = StdIn.readInt();
        int T = StdIn.readInt();
//        example(4);

        PercolationStats percolationStats = new PercolationStats(N, T);

        StdOut.println("mean = " + percolationStats.mean());
        StdOut.println("deviation = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }
}