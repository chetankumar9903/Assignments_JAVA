package com.aurionpro.assignment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.aurionpro.exception.CellAlreadyMarkedException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
	
	private Cell cell;
	@BeforeEach
	void init() {
		cell = new Cell();
	}

    @Test
    public void testCellInitiallyEmpty() {
//        Cell cell = new Cell();
        assertEquals(Mark.EMPTY, cell.getMark());
    }

    @Test
    public void testMarkingEmptyCell() throws CellAlreadyMarkedException {
//        Cell cell = new Cell();
        cell.markCell(Mark.X);
        assertEquals(Mark.X, cell.getMark());
    }

    @Test
    public void testMarkingAlreadyMarkedCell() throws CellAlreadyMarkedException {
        Cell cell = new Cell();
        cell.markCell(Mark.O);
        assertThrows(IllegalStateException.class, () -> cell.markCell(Mark.X));
    }
    
//    @Test
//    public void testMarkingAlreadyMarkedCell() throws CellAlreadyMarkedException {
////        Cell cell = new Cell();
//        cell.markCell(Mark.X);
//
//        Exception exception = assertThrows(CellAlreadyMarkedException.class, () -> {
//            cell.markCell(Mark.O);
//        });
//
//        assertEquals("Cell is already marked. Choose another cell.", exception.getMessage());
//    }

}
