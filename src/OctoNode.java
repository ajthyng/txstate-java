public class OctoNode {
    private OctoNode[] adjacentPieces = new OctoNode[8];
    private OctoNode next;
    private char pieceColor;
    private final char EMPTY_PIECE = 'E';

    private final int TOP_LEFT = 0;
    private final int TOP = 1;
    private final int TOP_RIGHT = 2;
    private final int RIGHT = 3;
    private final int BOTTOM_RIGHT = 4;
    private final int BOTTOM = 5;
    private final int BOTTOM_LEFT = 6;
    private final int LEFT = 7;

    public OctoNode (char color) {
        this.pieceColor = color;
        setAdjacentPiecesNull();
    }

    public OctoNode getNextDirection(int index) {
        return adjacentPieces[index];
    }

    public char getPieceColor() {
        return pieceColor;
    }

    public OctoNode getNext() {
        return this.next;
    }

    public void setNext(OctoNode next) {
        this.next = next;
    }

    public void setTopLeft(OctoNode topLeft) {
        adjacentPieces[TOP_LEFT] = topLeft;
    }

    public void setTop(OctoNode top) {
        adjacentPieces[TOP] = top;
    }

    public void setTopRight(OctoNode topRight) {
        adjacentPieces[TOP_RIGHT] = topRight;
    }

    public void setRight(OctoNode right) {
        adjacentPieces[RIGHT] = right;
    }

    public void setBottomRight(OctoNode bottomRight) {
        adjacentPieces[BOTTOM_RIGHT] = bottomRight;
    }

    public void setBottom(OctoNode bottom) {
        adjacentPieces[BOTTOM] = bottom;
    }

    public void setBottomLeft(OctoNode bottomLeft) {
        adjacentPieces[BOTTOM_LEFT] = bottomLeft;
    }

    public void setLeft(OctoNode left) {
        adjacentPieces[LEFT] = left;
    }

    public boolean isEmpty() {
        return this.pieceColor == EMPTY_PIECE;
    }

    /**
     * Checks all 8 possible directions to see if there are four consecutive pieces
     * in any direction on the board.
     * @return True if there are four consecutive pieces.
     */
    public boolean containsFourConsecutivePieces() {
        if (isEmpty()) return false;

        for (int direction = 0; direction < adjacentPieces.length; direction++) {
            if (hasFourInOneDirection(direction)) return true;
        }

        return false;
    }

    /**
     * Counts up to 3 pieces in the specified direction, checking if the are the same as this piece.
     * @param direction A numeric representation of the direction to check for four consecutive pieces.
     * @return True if there are four consecutive pieces in the direction provided.
     */
    private boolean hasFourInOneDirection(int direction) {
        int totalPiecesCounted = 1;
        char originalPieceColor = pieceColor;
        OctoNode nodeIterator = adjacentPieces[direction];

        if (nodeIterator == null) return false;

        while (nodeIterator != null && totalPiecesCounted < 4 && originalPieceColor == nodeIterator.getPieceColor()) {
            nodeIterator = nodeIterator.getNextDirection(direction);
            totalPiecesCounted++;
        }
        return totalPiecesCounted >= 4;
    }

    private void setAdjacentPiecesNull() {
        for (int i = 0; i < adjacentPieces.length; i++) {
            adjacentPieces[i] = null;
        }
    }
}
