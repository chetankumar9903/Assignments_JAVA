package com.aurionpro.assignment;

import com.aurionpro.exception.CellAlreadyMarkedException;
import com.aurionpro.exception.InvalidCellException;

public class Board {
    private final int SIZE = 3;
    private final Cell[][] cells;

    public Board() {
        cells = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

//    public void markCell(int row, int col, Mark mark) {
//        cells[row][col].markCell(mark);
//    }

    
    public void markCell(int row, int col, Mark mark) throws InvalidCellException, CellAlreadyMarkedException {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            throw new InvalidCellException(" Invalid position! Row and Column must be between 0 and 2.");
        }

        cells[row][col].markCell(mark);  
    }

    public boolean isCellEmpty(int row, int col) {
        return cells[row][col].isEmpty();
    }

    public Mark getCellMark(int row, int col) {
        return cells[row][col].getMark();
    }

    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(cells[i][j].getMark() + "\t");
            }
            System.out.println();
        }
    }

    public boolean checkWin(Mark mark) {
        // Check rows
        for (int i = 0; i < SIZE; i++) {
            if (cells[i][0].getMark() == mark &&
                cells[i][1].getMark() == mark &&
                cells[i][2].getMark() == mark) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < SIZE; j++) {
            if (cells[0][j].getMark() == mark &&
                cells[1][j].getMark() == mark &&
                cells[2][j].getMark() == mark) {
                return true;
            }
        }

        // Check diagonals
        if (cells[0][0].getMark() == mark &&
            cells[1][1].getMark() == mark &&
            cells[2][2].getMark() == mark) {
            return true;
        }

        if (cells[0][2].getMark() == mark &&
            cells[1][1].getMark() == mark &&
            cells[2][0].getMark() == mark) {
            return true;
        }

        return false;
    }
    
    public void resetBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = new Cell(); 
            }
        }
    }

}
