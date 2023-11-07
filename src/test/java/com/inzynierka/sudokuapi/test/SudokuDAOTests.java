package com.inzynierka.sudokuapi.test;

import com.inzynierka.sudokuapi.Models.DAO.SudokuDAO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class SudokuDAOTests {
    @Test
    void isCorrect_WithDuplicateNumbersInRow_False() {
        SudokuDAO sudoku = SudokuDAO.getBaseBoard();
        sudoku.setValue(1, 9, 1);
        log.info(String.format("Testing is correct for board: \n%s", sudoku));
        Assertions.assertFalse(sudoku.isCorrect());
    }

    @Test
    void isCorrect_WithDuplicateNumbersInColumn_False() {
        SudokuDAO sudoku = SudokuDAO.getBaseBoard();
        sudoku.setValue(9, 1, 1);
        log.info(String.format("Testing is correct for board: \n%s", sudoku));
        Assertions.assertFalse(sudoku.isCorrect());
    }

    @Test
    void isCorrect_WithDuplicateNumbersInSquare_False() {
        SudokuDAO sudoku = SudokuDAO.getBaseBoard();
        sudoku.setValue(3, 3, 1);
        log.info(String.format("Testing is correct for board: \n%s", sudoku));
        Assertions.assertFalse(sudoku.isCorrect());
    }

    @Test
    void isCorrect_WithBaseBoard_True() {
        SudokuDAO sudoku = SudokuDAO.getBaseBoard();
        log.info(String.format("Testing is correct for board: \n%s", sudoku));
        Assertions.assertTrue(sudoku.isCorrect());
    }

    @Test
    void isCorrect_WithDiagonalRandomValues_True() {
        SudokuDAO sudoku = SudokuDAO.getBoardWithDiagonalValues();
        log.info(String.format("Testing is correct for board: \n%s", sudoku));
        Assertions.assertTrue(sudoku.isCorrect());
    }

    @Test
    void hasAllValues_WithSomeValuesMissing_False() {
        SudokuDAO sudoku = SudokuDAO.getBaseBoard();
        sudoku.setValue(1, 1, 0);
        log.info(String.format("Testing hasAllValues for board: \n%s", sudoku));
        Assertions.assertFalse(sudoku.hasAllValues());
    }

    @Test
    void hasAllValues_WithAllValues_True() {
        SudokuDAO sudoku = SudokuDAO.getBaseBoard();
        log.info(String.format("Testing hasAllValues for board: \n%s", sudoku));
        Assertions.assertTrue(sudoku.hasAllValues());
    }
}
