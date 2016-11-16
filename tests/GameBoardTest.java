import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {
    private GameBoard testBoard;
    private char black, red;

    @Before
    public void setUp() {
        testBoard = new GameBoard();
        black = 'B';
        red = 'R';
    }

    @Test
    public void iteratorIteratesThroughElements() {
        int expectedIterations = 0;
        int desiredSize = 10;
        char first = 'A';
        for (int i = 0; i < desiredSize; i++) {
            testBoard.add(new OctoNode(first++));
            expectedIterations++;
        }
        int actualIterations = 0;
        first = 'A';
        for (OctoNode node : testBoard) {
            Character piece = node.getPieceColor();
            assertTrue("did not receive elements in order", piece.equals(first++));
            actualIterations++;
        }
        assertEquals("iterated over the wrong number of elements", expectedIterations, actualIterations);
    }

    @Test
    public void gameBoardChecksTopLeftAndBottomRight() {
        OctoNode one = new OctoNode(black);
        OctoNode two = new OctoNode(black);
        OctoNode three = new OctoNode(black);
        OctoNode four = new OctoNode(black);

        one.setTopLeft(two);
        two.setTopLeft(three);
        three.setTopLeft(four);

        four.setBottomRight(three);
        three.setBottomRight(two);
        two.setBottomRight(one);

        testBoard.add(one);
        testBoard.add(two);
        testBoard.add(three);
        testBoard.add(four);

        assertTrue("top left test failed four in a row", testBoard.nodeHasFourConsecutive(one));
        assertTrue("bottom right test failed four in a row", testBoard.nodeHasFourConsecutive(four));
    }

    @Test
    public void gameBoardChecksTopRightAndBottomLeft() {
        OctoNode one = new OctoNode(red);
        OctoNode two = new OctoNode(red);
        OctoNode three = new OctoNode(red);
        OctoNode four = new OctoNode(red);

        one.setTopRight(two);
        two.setTopRight(three);
        three.setTopRight(four);

        four.setBottomLeft(three);
        three.setBottomLeft(two);
        two.setBottomLeft(one);

        testBoard.add(one);
        testBoard.add(two);
        testBoard.add(three);
        testBoard.add(four);

        assertTrue("top right test failed four in a row", testBoard.nodeHasFourConsecutive(one));
        assertTrue("bottom left test failed four in a row", testBoard.nodeHasFourConsecutive(four));
    }

    @Test
    public void gameBoardChecksTopAndBottom() {
        OctoNode one = new OctoNode(red);
        OctoNode two = new OctoNode(red);
        OctoNode three = new OctoNode(red);
        OctoNode four = new OctoNode(red);

        one.setTop(two);
        two.setTop(three);
        three.setTop(four);

        four.setBottom(three);
        three.setBottom(two);
        two.setBottom(one);

        testBoard.add(one);
        testBoard.add(two);
        testBoard.add(three);
        testBoard.add(four);

        assertTrue("top test failed four in a row", testBoard.nodeHasFourConsecutive(one));
        assertTrue("bottom test failed four in a row", testBoard.nodeHasFourConsecutive(four));
    }

    @Test
    public void gameBoardChecksLeftAndRight() {
        OctoNode one = new OctoNode(black);
        OctoNode two = new OctoNode(black);
        OctoNode three = new OctoNode(black);
        OctoNode four = new OctoNode(black);

        one.setRight(two);
        two.setRight(three);
        three.setRight(four);

        four.setLeft(three);
        three.setLeft(two);
        two.setLeft(one);

        testBoard.add(one);
        testBoard.add(two);
        testBoard.add(three);
        testBoard.add(four);

        assertTrue("right test failed four in a row", testBoard.nodeHasFourConsecutive(one));
        assertTrue("left test failed four in a row", testBoard.nodeHasFourConsecutive(four));
    }

    @Test
    public void gameBoardLoadsAndDoesNotHaveFourConsecutive() {
        String filename = "noneInARow.txt";
        testBoard.loadFromFile(filename);
        assertFalse(filename + " had a false positive four consecutive pieces", testBoard.boardHasFourConsecutive());
    }

    @Test
    public void gameBoardLoadsAndHasFourConsecutive() {
        String filename = "hasFourTestFile.txt";
        testBoard.loadFromFile(filename);
        assertTrue(filename + " failed to recognize four consecutive pieces", testBoard.boardHasFourConsecutive());
    }

    @Test
    public void gameBoardFourCornersFilledInDoesNotHaveFourConsecutive() {
        String filename = "largerGridTest.txt";
        testBoard.loadFromFile(filename);
        assertFalse(filename + " had a false positive four consecutive pieces", testBoard.boardHasFourConsecutive());
    }
}