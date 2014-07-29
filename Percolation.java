public class Percolation {
    private int size;
    private boolean[][] arr;
    private WeightedQuickUnionUF wqUF = null;

    /**
     * Create N-by-N grid, with all sites blocked
     * 
     * @param N
     */
    public Percolation(int N) {
	if (N <= 0) {
	    throw new IllegalArgumentException("Inputs provided are illegal");
	}
	size = N;
	wqUF = new WeightedQuickUnionUF(N * N + 2);
	arr = new boolean[size][size];
	for (int i = 0; i < size; i++) {
	    for (int j = 0; j < size; j++) {
		arr[i][j] = false; // block the sites
	    }
	}

    }

    /**
     * Open site (row i, column j) if it is not already
     * 
     * @param i
     * @param j
     */
    public void open(int i, int j) {
	if (i < 1 || j < 1 || i > size || j > size) {
	    throw new IndexOutOfBoundsException("Input(s) provided are outside the range.");
	}
	if (isOpen(i, j)) {
	    return;
	}

	arr[i - 1][j - 1] = true;

	if (i > 1 && isOpen(i - 1, j)) { // Check with top block
	    wqUF.union((i - 2) * size + j - 1, (i - 1) * size + j - 1);
	} else if (i == 1) { // Virtual top point
	    wqUF.union((i - 1) * size + j - 1, size * size);
	}

	if (i < size && isOpen(i + 1, j)) { // Check with bottom block
	    wqUF.union((i) * size + j - 1, (i - 1) * size + j - 1);
	} else if (i == size) { // Virtual bottom point
	    wqUF.union((i - 1) * size + j - 1, size * size + 1);
	}

	if (j > 1 && isOpen(i, j - 1)) { // Check with left block
	    wqUF.union((i - 1) * size + j - 2, (i - 1) * size + j - 1);
	}

	if (j < size && isOpen(i, j + 1)) { // Check with right block
	    wqUF.union((i - 1) * size + j, (i - 1) * size + j - 1);
	}
    }

    /**
     * Is site (row i, column j) open?
     * 
     * @param i
     * @param j
     * @return
     */
    public boolean isOpen(int i, int j) {
	if (i < 1 || j < 1 || i > size || j > size) {
	    throw new IndexOutOfBoundsException("Input(s) provided are outside the range.");
	}
	return arr[i - 1][j - 1];
    }

    /**
     * Is site (row i, column j) full?
     * 
     * @param i
     * @param j
     * @return
     */
    public boolean isFull(int i, int j) {
	if (i < 1 || j < 1 || i > size || j > size) {
	    throw new IndexOutOfBoundsException("Input(s) provided are outside the range.");
	}
	return wqUF.connected(size * size, (i - 1) * size + j - 1);
	/*
	 * for (int k = 0; k < size; k++) { if (isOpen(1, k+1) &&
	 * wqUF.connected(k, (i-1)*size+j-1)) { return true; } } return false;
	 */
    }

    /**
     * Does site (row N, column N) percolates?
     * 
     * @return
     */
    public boolean percolates() {
	/*
	 * for (int i = 0; i < size; i++) { if (isOpen(1, i+1)) { for (int j =
	 * 0; j < size; j++) { if (isOpen(size, j+1) && wqUF.connected(i,
	 * (size-1)*size+j)) return true; } } } return false;
	 */
	return wqUF.connected(size * size, size * size + 1);
    }
}
