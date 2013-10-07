/**
 * Auto Generated Java Class.
 */
public class Percolation {
    public int[][] grid;

    public Percolation(int N)              // create N-by-N grid, with all sites blocked
    {
        grid = new int[N][N];
    }

    public void open(int i, int j)         // open site (row i, column j) if it is not already
    {
        grid[i][j] = 1;
    }

    public boolean isOpen(int i, int j)    // is site (row i, column j) open?
    {
        return grid[i][j] != 0;
    }

    public boolean isFull(int i, int j)    // is site (row i, column j) full?
    {
        return grid[i][j] == 0;
    }

    public boolean percolates()            // does the system percolate?
    {
        WeightedQuickUnionUF weightedQuickUnionUF = new WeightedQuickUnionUF(10);
        weightedQuickUnionUF.connected(1, 1);

        return true;
    }
}