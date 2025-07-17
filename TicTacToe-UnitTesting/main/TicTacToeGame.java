package com.aurionpro.assignment;

import com.aurionpro.exception.CellAlreadyMarkedException;
import com.aurionpro.exception.InvalidCellException;

public class TicTacToeGame {
	 private Board board;
	    private Player player1;
	    private Player player2;
	    private Player currentPlayer;
	    private int moves;

	    public TicTacToeGame(String player1Name, String player2Name) {
	        this.player1 = new Player(player1Name, Mark.X);
	        this.player2 = new Player(player2Name, Mark.O);
	        this.currentPlayer = player1;
	        this.board = new Board();
	        this.moves = 0;
	    }

	    public void printBoard() {
	        board.printBoard();
	    }

//	    public String makeMove(int row, int col) {
//	        try {
//	            board.markCell(row, col, currentPlayer.getMark());
//	            moves++;
//
//	            if (board.checkWin(currentPlayer.getMark())) {
//	                String winner = currentPlayer.getName();
//	                return winner + " wins!";
//	            }
//
//	            if (moves == 9) {
//	                return "😐 It's a draw!";
//	            }
//
//	            switchPlayer();
//	            return "Move accepted. Next turn: " + currentPlayer.getName();
//
//	        } catch (IllegalStateException e) {
//	            return "Cell already marked. Try again.";
//	        }
//	    }
	    
	    public String makeMove(int row, int col) {
	        try {
	            board.markCell(row, col, currentPlayer.getMark());
	            moves++;

	            if (board.checkWin(currentPlayer.getMark())) {
	                return currentPlayer.getName() + " wins!";
	            }

	            if (moves == 9) {
	                return "It's a draw!";
	            }

	            switchPlayer();
	            return "Move accepted. Next turn: " + currentPlayer.getName();

	        } catch (InvalidCellException | CellAlreadyMarkedException e) {
	            return e.getMessage();  // return exception message to UI
	        }
	    }


	    public void resetGame() {
	        board.resetBoard();
	        currentPlayer = player1;
	        moves = 0;
	    }

	    private void switchPlayer() {
	        currentPlayer = (currentPlayer == player1) ? player2 : player1;
	    }

	    public Player getCurrentPlayer() {
	        return currentPlayer;
	    }

	    public boolean isGameOver() {
	        return moves >= 9 || board.checkWin(player1.getMark()) || board.checkWin(player2.getMark());
	    }

}
