import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Model percolation across a n-by-n grid using {@link WeightedQuickUnionUF} to maintain connections across open sites in the grid
 * @author Sudarshan R Thitte
 */
public class Percolation {

    /**
     * A n-by-n grid through whose sites, this grid's percolation factor is being modeled
     */
    int [][] site;
    
    /**
     * Maintain possible future connections across opened sites towards modeling this grid's percolation factor
     * {@see WeightedQuickUnionUF} for more information on how these connections are established and maintained
     */
    WeightedQuickUnionUF uf;
    
    /**
     * Number of open sites in this grid
     */
    int numOpenSites;

    /**
     * Initialize this grid such that all its sites are blocked to ensure it begins without any ability to percolate. Also
     * initialize the data type to hold future connections across open sites as the percolation model of this grid evolves.
     * @param n {@code int} value representing the order of this grid
     * @throws IllegalArgumentException when {@code n} is a non-positive value
     */
    public Percolation(int n) {
        
        // validate grid order
        if (n <= 0)
            throw new java.lang.IllegalArgumentException ("Grid order must be a non-zero positive value");

        // initialize n-by-n grid to be fully blocked
        site = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n;  j++)
                site [i][j] = 0;
        
        // use a union-find data type to accommodate all sites from the grid should they be opened up in future
        uf = new WeightedQuickUnionUF(n^2);
        numOpenSites = 0;
    }
    
    /**
     * Is current index point {@code ix} invalid , or outside the acceptable range [0,n) ?
     * @param ix {@code int} index point to be examined
     * @return {@code boolean} value of {@code true} if invalid; {@code false} if valid
     */
    private boolean isInvalid (int ix) {
        return (ix <0 || ix >= site.length) ? true : false;
    } 
    
    /**
     * Open the current site in the grid. This translates into two operations: <br>
     * <li>1. Set the site's value in this grid to be {@code 1}</li>
     * <li>2. Connect this now-open site to any neighboring open sites. A neighboring open site is any 
     * open site found to the top or bottom or left or right, sides of this site.</li>
     * Connections across open sites is maintained using the {@link WeightedQuickUnionUF} data type
     * @param row {@code int} value representing the row index of the current site in the grid
     * @param col {@code int} value representing the column index of the current site in the grid 
     * @throws IndexOutOfBoundsException when either (or both) of {@code row} and {@code col} is (or are) 
     * outside the acceptable range of [0,n)
     */
    public void open(int row, int col) {
        
        // invalid index points are unacceptable
        if (isInvalid(row) || isInvalid (col))
            throw new java.lang.IndexOutOfBoundsException ("Input index point(s) must be within the acceptable range of [0,n)");
        
        // open site (row, col) if it is not open already
        // once opened, connect to neighboring open sites, if any
        else if (site [row][col] == 0) {
            site [row][col] = 1;
            
            // TODO Avoid going out of bounds either ways, when seeking array values
            
            if (isOpen(row-1,col)) // top-neighbor
                uf.union(site[row][col], site[row-1][col]);
            if (isOpen(row+1,col)) // bottom-neighbor
                uf.union(site[row][col], site[row+1][col]);
            if (isOpen(row,col-1)) // left-neighbor
                uf.union(site[row][col], site[row][col-1]);
            if (isOpen(row,col+1)) // right-neighbor
                uf.union(site[row][col], site[row][col+1]);    
        }

        ++numOpenSites;
    }

    /**
     * Is the current site in the grid, open ? <br>
     * The current site in the grid is deemed to be open if its value in the grid is {@code 1} and closed if its value in the grid is {@code 0}
     * @param row {@code int} value representing the row index of the current site in the grid
     * @param col {@code int} value representing the column index of the current site in the grid
     * @return {@code boolean} value of {@code true} if it is open; {@code false} if it is closed
     * @throws IndexOutOfBoundsException when either (or both) of {@code row} and {@code col} is (or are) 
     * outside the acceptable range of [0,n)
     */
    public boolean isOpen(int row, int col) {
        
        // invalid index points are unacceptable
        if (isInvalid(row) || isInvalid (col))
            throw new java.lang.IndexOutOfBoundsException ("Input index point(s) must be within the acceptable range of [0,n)");
            
        // is site (row, col) open?
        else if (site [row][col] == 1)
            return true;
        
        else
            return false;
    }

    /**
     * Is the current site in the grid, full ? <br>
     * The current site is deemed to be full if it is open and it has a connection to another open site from the top row of its grid
     * such that every site along this path is comprised of other open sites in this grid.
     * @param row {@code int} value representing the row index of the current site in the grid
     * @param col {@code int} value representing the column index of the current site in the grid
     * @return {@code boolean} value of {@code true} if it is full; {@code false} if it isn't
     * @throws IndexOutOfBoundsException when either (or both) of {@code row} and {@code col} is (or are) 
     * outside the acceptable range of [0,n)
     */
    public boolean isFull(int row, int col) {
        
        // invalid index points are unacceptable
        if (isInvalid(row) || isInvalid (col))
            throw new java.lang.IndexOutOfBoundsException ("Input index point(s) must be within the acceptable range of [0,n)");
        
        // is site (row, col) full?
        else if (isOpen(row, col)) {
            for (int topRowColIx = 0; topRowColIx < site.length; topRowColIx ++)
                if (isOpen(0, topRowColIx) && uf.connected(site[row][col], site[0][topRowColIx]))
                    return true;
        }
        
        return false;
    }

    /**
     * Yield the number of currently open sites in the grid
     * @return {@code int} value of number of open sites in the grid at present
     */
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    /**
     * In its current state, does the grid percolate ? <br>
     * The grid is deemed to percolate if any of its bottom open sites are full. 
     * @return boolean {@code true} if it does; {@code false} if it doesn't.
     */
    public boolean percolates() {
        // does the system percolate?
        for (int bottomRowColIx = 0; bottomRowColIx < site.length; bottomRowColIx++)
            if (isFull(site.length - 1, bottomRowColIx))
                return true;
        return false;
    }

    /**
     * Optional test driver
     */
    public static void main(String[] args) {
        // test client (optional)
    }
}
