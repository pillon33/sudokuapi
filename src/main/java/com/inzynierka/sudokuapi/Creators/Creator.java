package com.inzynierka.sudokuapi.Creators;

import com.inzynierka.sudokuapi.Models.DAO.SudokuDAO;

public interface Creator {
    /**
     * Creates full board (with all values).
     * @return sudoku board with all values
     */
    public SudokuDAO createBoard();

    /**
     * Creates mask for given board to create puzzle.
     * @param sudoku board with all values and empty mask
     * @return board with given values and random mask
     */
    public SudokuDAO createPuzzle(SudokuDAO sudoku);
}
