package com.lee.tdd4java.ch05;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Connect4TDDTest {
	
	private Connect4TDD tested;
	private ExpectedException exception = ExpectedException.none();
	private OutputStream output;
	
	@Before
	public void before() {
		output = new ByteArrayOutputStream();
		tested = new Connect4TDD(new PrintStream(output));
	}
	
	@Test
	public void whenTheGameIsStartedTheBoardIsEmpty() {
		assertThat(tested.getNumberOfDiscs(), is(0));
	}
	
	@Test(expected = RuntimeException.class)
	public void whenDiscsOutsideTheBoardThenRuntimeException() {
		int column = -1;
		//exception.expect(RuntimeException.class);
		//exception.expectMessage("Invalid column " +column);
		tested.putDiscInColumn(column);
	}
	
	@Test
	public void whenFirstDiscInsertInColumnThenPositionIsZero() {
		int column = 1;
		assertThat(tested.putDiscInColumn(column), is(0));
	}
	
	@Test
	public void whenSecondDiscInsertInColumnThenPositionIsOne() {
		int column = 1;
		tested.putDiscInColumn(column);
		assertThat(tested.putDiscInColumn(column), is(1));
	}
	
	@Test
	public void whenDiscInsertedThenNumberOfDiscsIncreases() {
		int column = 1;
		tested.putDiscInColumn(column);
		assertThat(tested.getNumberOfDiscs(), is(1));
	}
	
	@Test(expected = RuntimeException.class)
	public void whenNoMoreRoomInColumnThenRuntimeException() {
		int column = 1;
		int maxDiscsInColumn = 6; // the number of rows
		for (int times = 0; times < maxDiscsInColumn; ++times) {
			tested.putDiscInColumn(column);
		}
		//exception.expect(RuntimeException.class);
		//exception.expectMessage("No more room in column " + column);
		tested.putDiscInColumn(column);
	}
	
	@Test
	public void whenFirstPlayerPlaysThenDiscsColorRed() {
		assertThat(tested.getCurrentPlayer(), is("R"));
	}
	
	@Test
	public void whenSecondPlayerPlaysThenDiscsColorGreen() {
		int column = 1;
		tested.putDiscInColumn(column);
		assertThat(tested.getCurrentPlayer(), is("G"));
	}
	
	@Test
	public void whenAskedForCurrentPlayerTheOutputNotice() {
		tested.getCurrentPlayer();
		assertThat(output.toString(), containsString("Player R turn"));
	}

	@Test
	public void whenADiscIsIntroducedTheBoardIsPrinted() {
		int column = 1;
		tested.putDiscInColumn(column);
		assertThat(output.toString(), containsString("| |R| | | | | |"));
	}
	
	@Test
	public void whenTheGameStartsItIsNotFinished() {
		assertFalse("The game must not be finished", tested.isFinished());
	}

	@Test
	public void whenNoDiscCanBeIntroducedTheGamesIsFinished() {
		for (int row = 0; row < 6; row++)
			for (int column = 0; column < 7; column++)
				tested.putDiscInColumn(column);
		assertTrue("The game must be finished", tested.isFinished());
	}
	
	@Test
	public void when4VerticalDiscsAreConnectedThenPlayerWins() {
		for (int row = 0; row < 3; row++) {
			tested.putDiscInColumn(1); // R
			tested.putDiscInColumn(2); // G
		}
		assertThat(tested.getWinner(), isEmptyString());
		tested.putDiscInColumn(1); // R
		assertThat(tested.getWinner(), is("R"));
	}
	
	@Test
	public void when4HorizontalDiscsAreConnectedThenPlayerWins() {
		int column;
		for (column = 0; column < 3; column++) {
			tested.putDiscInColumn(column); // R
			tested.putDiscInColumn(column); // G
		}
		assertThat(tested.getWinner(), isEmptyString());
		tested.putDiscInColumn(column); // R
		assertThat(tested.getWinner(), is("R"));
	}
}
