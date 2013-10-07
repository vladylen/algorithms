public class Runner {
    public static void main(String[] args) {
        Percolation percolation = new Percolation(10);
        int i = percolation.grid[1];
        boolean bool1 = percolation.isFull(1, 1);
        boolean bool2 = percolation.isOpen(1, 1);
        boolean bool3 = percolation.percolates();
        percolation.open(1, 1);
        boolean bool4 = percolation.isFull(1, 1);
        boolean bool5 = percolation.isOpen(1, 1);
        boolean bool6 = percolation.percolates();
    }
}