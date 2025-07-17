package com.aurionpro.assignment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.aurionpro.exception.CellAlreadyMarkedException;
import com.aurionpro.exception.InvalidCellException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testRowWin() throws InvalidCellException, CellAlreadyMarkedException {
        Board board = new Board();
        board.markCell(0, 0, Mark.X);
        board.markCell(0, 1, Mark.X);
        board.markCell(0, 2, Mark.X);

        assertTrue(board.checkWin(Mark.X));
    }

    @Test
    public void testColumnWin() throws InvalidCellException, CellAlreadyMarkedException {
        Board board = new Board();
        board.markCell(0, 0, Mark.O);
        board.markCell(1, 0, Mark.O);
        board.markCell(2, 0, Mark.O);

        assertTrue(board.checkWin(Mark.O));
    }

    @Test
    public void testDiagonalWin() throws InvalidCellException, CellAlreadyMarkedException {
        Board board = new Board();
        board.markCell(0, 0, Mark.X);
        board.markCell(1, 1, Mark.X);
        board.markCell(2, 2, Mark.X);

        assertTrue(board.checkWin(Mark.X));
    }

    @Test
    public void testAntiDiagonalWin() throws InvalidCellException, CellAlreadyMarkedException {
        Board board = new Board();
        board.markCell(0, 2, Mark.O);
        board.markCell(1, 1, Mark.O);
        board.markCell(2, 0, Mark.O);

        assertTrue(board.checkWin(Mark.O));
    }

    @Test
    public void testNoWin() throws InvalidCellException, CellAlreadyMarkedException {
        Board board = new Board();
        board.markCell(0, 0, Mark.X);
        board.markCell(0, 1, Mark.O);
        board.markCell(0, 2, Mark.X);

        assertFalse(board.checkWin(Mark.X));
    }
}
