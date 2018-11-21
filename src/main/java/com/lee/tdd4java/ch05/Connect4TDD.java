package com.lee.tdd4java.ch05;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Connect4TDD {
	
	public static final int COLUMNS = 7; 
	public static final int ROWS = 6; 
	private static final String EMPTY = " ";
	private static final String RED = "R";
	private static final String GREEN = "G";
	private String currentPlayer = RED;
	private final String DELIMITER = "|";
	private PrintStream outputChannel;
	private static final int DISCS_TO_WIN = 4;
	private String winner = "";
	
	private String [][] board = new String[ROWS][COLUMNS];
	
//	public Connect4TDD() {
//		for (String[] row : board) {
//			Arrays.fill(row, EMPTY);
//		}
//	}
	
	public Connect4TDD(PrintStream output) {
		outputChannel = output;
		for (String[] row : board) {
			Arrays.fill(row, EMPTY);
		}
	}

	public int getNumberOfDiscs() {
		return IntStream.range(0, COLUMNS)
				.map(this::getNumberOfDiscsInColumn).sum();
	}

	private int getNumberOfDiscsInColumn(int column) {
		return (int) IntStream.range(0, ROWS)
				.filter(row -> !EMPTY.equals(board[row][column])).count();
	}

	public int putDiscInColumn(int column) {
		checkColumn(column);
		int row = getNumberOfDiscsInColumn(column);
		checkPositionToInsert(row, column);
		board[row][column] = getCurrentPlayer();
		checkWinner(row, column);
		printBoard();
		switchPlayer();
		return row;
	}
	
	private void checkColumn(int column) {
		if (column < 0 || column >= COLUMNS) {
			throw new RuntimeException("Invalid column " + column);
		}
	}
	
	private void checkPositionToInsert(int row, int column) {
		if (row == ROWS) {
			throw new RuntimeException("No more room in column " + column);
		}
	}
	
	public static void main(String[] args) {
		IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
		System.out.println("==================================");
		IntStream.range(1, 3).forEach(System.out::println);
		System.out.println("==================================");
		IntStream.rangeClosed(1, 3).forEach(System.out::println);
	}

	public String getCurrentPlayer() {
		outputChannel.printf("Player %s turn", currentPlayer);
		return this.currentPlayer;
	}
	
	private void switchPlayer() {
		if (RED.equals(this.currentPlayer)) {
			this.currentPlayer = GREEN;
		} else {
			this.currentPlayer = RED;
		}
	}
	
	private void printBoard() {
		for (int row = ROWS - 1; row >= 0; row--) {
			StringJoiner stringJoiner = new StringJoiner(DELIMITER, DELIMITER, DELIMITER);
			Stream.of(board[row]).forEachOrdered(stringJoiner::add);
			outputChannel.println(stringJoiner.toString());
		}
	}

	public boolean isFinished() {
		return getNumberOfDiscs() == ROWS * COLUMNS;
	}

	public String getWinner() {
		return this.winner;
	}
	
	private void checkWinner(int row, int column) {
		if (winner.isEmpty()) {
			String colour = board[row][column];
			//".*[RG]{4}.*"
			Pattern winPattern = Pattern.compile(".*" + colour + "{" + DISCS_TO_WIN + "}.*");
			String vertical = IntStream.range(0, ROWS).mapToObj(r -> board[r][column]).reduce(String::concat).get();
			if (winPattern.matcher(vertical).matches()) {
				winner = colour;
			}
			
			if (winner.isEmpty()) {
				String horizontal = Stream.of(board[row]).reduce(String::concat).get();
				if (winPattern.matcher(horizontal).matches())
					winner = colour;
			}
		}

	}
}
