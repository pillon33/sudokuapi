package com.sudoku.api.Factories.Abstract;

import com.sudoku.api.Models.DAO.SudokuDAO;

public interface SudokuFactory {
    /**
     * Creates full board (with all values).
     * @return sudoku board with all values
     */
    SudokuDAO createBoard();

    /**
     * Creates mask for given board to create puzzle.
     * @param sudoku board with all values and empty mask
     * @return board with given values and random mask
     */
    SudokuDAO createPuzzle(SudokuDAO sudoku);

    /**
     * Returns puzzle using implemented methods createBoard and createPuzzle
     * @return Sudoku puzzle
     */
    default SudokuDAO create() {
        return this.createPuzzle(this.createBoard());
    }
}
