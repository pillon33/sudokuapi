package com.sudoku.api.Factories.Concrete;

import com.sudoku.api.Factories.Abstract.SudokuFactory;
import com.sudoku.api.Models.DAO.SudokuDAO;

public class BaseSudokuFactory implements SudokuFactory {
    @Override
    public SudokuDAO createBoard() {
        return null;
    }

    @Override
    public SudokuDAO createPuzzle(SudokuDAO sudoku) {
        return sudoku;
    }
}
