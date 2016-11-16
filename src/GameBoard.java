import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GameBoard implements Iterable<OctoNode> {
    private OctoNode firstSquare;
    private OctoNode lastSquare;

    private int gameBoardPieces;

    public GameBoard () {
        this.firstSquare = null;
        this.lastSquare = null;
        this.gameBoardPieces = 0;
    }

    /**
     * Adds a new piece to the board. The pieces are added left to right, top to bottom.
     * @param newLastPiece OctoNode with appropriate adjacency relationships set.
     */
    public void add(OctoNode newLastPiece) {
        if (this.firstSquare == null) {
            this.firstSquare = newLastPiece;
            this.lastSquare = newLastPiece;
            this.gameBoardPieces += 1;
        } else {
            OctoNode secondToLast = this.lastSquare;
            secondToLast.setNext(newLastPiece);
            this.lastSquare = newLastPiece;
            this.gameBoardPieces += 1;
        }
    }

    /**
     *
     * @return The number of squares on the game board.
     */
    public int size() {
        return this.gameBoardPieces;
    }

    /**
     * Loads a board from a file. Format Example:
     * 4 4
     * R R R R
     * B B B B
     * E E E E
     * B B B B
     * @param filename File name which is expected to be in the working directory.
     */
    public void loadFromFile(String filename) {
        Scanner scan = null;
        try {
            scan = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (scan == null) throw new NullPointerException(filename + " produced a null Scanner.");

        int totalRows = scan.nextInt();
        int totalCols = scan.nextInt();
        OctoNode[][] gridOfPieces = new OctoNode[totalRows][totalCols];
        String singlePiece = scan.next();
        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                gridOfPieces[row][col] = assignPieceNode(singlePiece);
                add(gridOfPieces[row][col]);
                if (scan.hasNext()) singlePiece = scan.next();
            }
        }

        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                setAdjacentSquares(gridOfPieces, row, col);
            }
        }
    }

    private OctoNode assignPieceNode(String piece) {
        return new OctoNode(stringToChar(piece));
    }

    private char stringToChar(String piece) {
        return piece.toCharArray()[0];
    }

    /**
     * This method sets the 8 possible adjacent square pointers on based on the grid provided. For example,
     * the top left square in the grid will have the right, bottom-right, and bottom adjacency pointers
     * set.
     * @param grid 2D Array of Grid objects to build adjacency relations with
     * @param row Current row
     * @param col Current column
     */
    private void setAdjacentSquares(OctoNode[][] grid, int row, int col) {
        int maxRowIndex = grid.length - 1;
        int maxColIndex = grid[0].length - 1;

        OctoNode piece = grid[row][col];
        if (rightSpotAvailable(col, maxColIndex)) {
            piece.setRight( grid[row][col+1] );
        }
        if (leftSpotAvailable(col)) {
            piece.setLeft( grid[row][col-1] );
        }
        if (bottomSpotAvailable(row, maxRowIndex)) {
            piece.setBottom( grid[row+1][col] );

            if (leftSpotAvailable(col)) piece.setBottomLeft( grid[row+1][col-1] );
            if (rightSpotAvailable(col, maxColIndex)) piece.setBottomRight( grid[row+1][col+1] );
        }
        if (topSpotAvailable(row)) {
            piece.setTop( grid[row-1][col] );

            if (rightSpotAvailable(col, maxColIndex)) piece.setTopRight( grid[row-1][col+1] );
            if (leftSpotAvailable(col)) piece.setTopLeft( grid[row-1][col-1] );
        }
    }

    private boolean rightSpotAvailable(int col, int max) { return col < max; }
    private boolean leftSpotAvailable(int col) { return col > 0; }
    private boolean bottomSpotAvailable(int row, int max) { return row < max; }
    private boolean topSpotAvailable(int row) { return row > 0; }

    public boolean boardHasFourConsecutive() {
        Iterator<OctoNode> nodeIterator = iterator();
        while (nodeIterator.hasNext()) {
            if (nodeHasFourConsecutive(nodeIterator.next())) return true;
        }
        return false;
    }

    public boolean nodeHasFourConsecutive(OctoNode piece) {
        return piece.containsFourConsecutivePieces();
    }

    public Iterator<OctoNode> iterator() {
        return new GameBoardIterator();
    }

    /**
     * Iterator class, mainly because I had a mid-term covering iterators in Java.
     */
    private class GameBoardIterator implements Iterator<OctoNode> {
        private OctoNode cursor;
        private int startingLength;

        private GameBoardIterator () {
            this.cursor = GameBoard.this.firstSquare;
            startingLength = GameBoard.this.size();
        }

        public boolean hasNext() {
            if (startingLength != GameBoard.this.size()) {
                throw new ConcurrentModificationException();
            }
            return this.cursor != null;
        }

        public OctoNode next() {
            if (this.startingLength != GameBoard.this.size()) {
                throw new ConcurrentModificationException();
            }

            if (this.hasNext()) {
                OctoNode current = this.cursor;
                this.cursor = this.cursor.getNext();
                return current;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
