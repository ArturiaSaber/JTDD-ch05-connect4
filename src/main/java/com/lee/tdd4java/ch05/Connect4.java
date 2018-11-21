package com.lee.tdd4java.ch05;

import java.util.Arrays;
import java.util.StringJoiner;

public class Connect4 {
	public enum Color {
		RED('R'), 
		GREEN('G'),
		EMPTY(' ');

		private final char value;

		Color(char value) {
			this.value = value;
		}

		public char value() {
			return this.value;
		}

		@Override
		public String toString() {
			return String.valueOf(this.value);
		}
	}
	
	public static final int COLUMNS = 7; 
	public static final int ROWS = 6; 
	
	private Color [][] board = new Color[COLUMNS][ROWS];
	
	private Connect4() {
		for (Color[] column : board) {
			Arrays.fill(column, Color.EMPTY);
		}
	}
	
	public void putDisc(int column) {
		if (column >= 1 && column <= COLUMNS) {
			int numOfDisc = getNumOfDisc(column-1);
			if (numOfDisc < ROWS) {
				board[column-1][numOfDisc] = currentPlayer;
				printBoard();
				switchPlayer();
			} else {
				System.out.println(numOfDisc);
				System.out.println("There's no room " + "for a new disc in this column");
				printBoard();
			}
		} else {
			System.out.println("Column out of bounds");
			printBoard();
		}
	}

	private int getNumOfDisc(int column) {
		if (column >= 0 && column <= COLUMNS-1) {
			int row;
			for (row = 0; row<ROWS; row++) {
				if (board[column][row] == Color.EMPTY) {
					return row;
				}
			}
			return row;
		}
		return -1;
	}
	
	private Color currentPlayer = Color.RED;
	
	private static final String DELIMITER = "|";

	private void switchPlayer() {
		if (Color.RED == currentPlayer) {
			currentPlayer = Color.GREEN;
		} else {
			currentPlayer = Color.RED;
		}
		System.out.println("Current turn: " + currentPlayer);
	}

	public void printBoard() {
		for (int row = ROWS - 1; row >= 0; --row) {
			StringJoiner stringJoiner = new StringJoiner(DELIMITER, DELIMITER, DELIMITER);
			for (int col = 0; col < COLUMNS; ++col) {
				stringJoiner.add(board[col][row].toString());
			}
			System.out.println(stringJoiner.toString());
		}
	}
}
