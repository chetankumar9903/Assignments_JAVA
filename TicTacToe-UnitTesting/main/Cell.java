package com.aurionpro.assignment;

import com.aurionpro.exception.CellAlreadyMarkedException;

public class Cell {
    private Mark mark;

    public Cell() {
        this.mark = Mark.EMPTY;
    }

    public Mark getMark() {
        return mark;
    }

//    public void markCell(Mark newMark) throws IllegalStateException {
//        if (this.mark != Mark.EMPTY) {
//            throw new IllegalStateException("Cell is already marked");
//        }
//        this.mark = newMark;
//    }
    
    public void markCell(Mark newMark) throws CellAlreadyMarkedException {
        if (this.mark != Mark.EMPTY) {
            throw new CellAlreadyMarkedException("Cell is already marked. Choose another cell.");
        }
        this.mark = newMark;
    }

    public boolean isEmpty() {
        return this.mark == Mark.EMPTY;
    }
}
