public class Percolation {
    private int[] grid;
    private int count;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf1;
    private int additionalTop;
    private int additionalBottom;

    public Percolation(int N)              // create N-by-N grid, with all sites blocked
    {
        count = N;
        int countUf = N * N + 2;
        additionalTop = 0;
        additionalBottom = countUf - 1;
        grid = new int[countUf];
        uf = new WeightedQuickUnionUF(countUf);

        uf1 = new WeightedQuickUnionUF(countUf-1);

        for (int k = 0; k < count; k++) {
            uf.union(additionalTop, k + 1);
            uf.union(additionalBottom, additionalBottom - k - 1);

            uf1.union(additionalTop, k + 1);
        }
    }

    public void open(int i, int j)         // open site (row i, column j) if it is not already
    {
        grid[position(i, j)] = 1;

        int[] neighbors = neighbors(i, j);

        for (int k = 0; k < neighbors.length; k++) {
            if (neighbors[k] > 0 && grid[neighbors[k]] > 0) {
                uf.union(position(i, j), neighbors[k]);

                uf1.union(position(i, j), neighbors[k]);
            }
        }
    }

    public boolean isOpen(int i, int j)    // is site (row i, column j) open?
    {
        return grid[position(i, j)] == 1;
    }

    public boolean isFull(int i, int j)    // is site (row i, column j) full?
    {
        return isOpen(i, j) && uf1.connected(position(i, j), additionalTop);
    }

    public boolean percolates()            // does the system percolate?
    {
        if (count == 1) {
            return isOpen(1, 1);
        } else {
            return uf.connected(additionalTop, additionalBottom);
        }
    }

    private int position(int i, int j) {
        i--;
        j--;
        if (i < count && j < count && i >= 0 && j >= 0) {
            return i * count + j + 1;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private int[] neighbors(int i, int j) {
        int[] neighbors = new int[4];

        int t = position(i, j) - count;
        int b = position(i, j) + count;
        int l = position(i, j) - 1;
        int r = position(i, j) + 1;

        if (t > 0) neighbors[0] = t;
        if (l > 0 && (l % count != 0)) neighbors[1] = l;
        if (b <= count * count) neighbors[2] = b;
        if (r <= count * count && (((r - 1) % count) != 0)) neighbors[3] = r;

        return neighbors;
    }
}