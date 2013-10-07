public class Percolation {
    public int[] grid;
    public int count;
    public int countUf;
    public UF uf;
    public int additionalTop;
    public int additionalBottom;

    public Percolation(int N)              // create N-by-N grid, with all sites blocked
    {
        count = N;
        countUf = N * N + 2;
        additionalTop = 0;
        additionalBottom = countUf - 1;
        grid = new int[countUf];
        uf = new UF(countUf);

        for (int k = 0; k < count; k++) {
            uf.union(additionalTop, k + 1);
            uf.union(additionalBottom, additionalBottom - k - 1);
        }
    }

    public void open(int i, int j)         // open site (row i, column j) if it is not already
    {
        grid[position(i, j)] = 1;

        int[] neighbors = neighbors(i, j);

        for (int k = 0; k < neighbors.length; k++) {
            if (neighbors[k] > 0 && grid[neighbors[k]] > 0) {
                uf.union(position(i, j), neighbors[k]);
            }
        }
    }

    public boolean isOpen(int i, int j)    // is site (row i, column j) open?
    {
        return uf.connected(additionalTop, position(i, j)) || uf.connected(position(i, j), additionalBottom);
    }

    public boolean isFull(int i, int j)    // is site (row i, column j) full?
    {
        return percolates() && uf.connected(additionalTop, position(i, j));
    }

    public boolean percolates()            // does the system percolate?
    {
        return uf.connected(additionalTop, additionalBottom);
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
        if (l > 0) neighbors[1] = l;
        if (b < count * count) neighbors[2] = b;
        if (r < count * count) neighbors[3] = r;

        return neighbors;
    }
}