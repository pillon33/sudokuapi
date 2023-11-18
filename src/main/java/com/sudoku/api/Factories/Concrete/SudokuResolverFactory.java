package com.sudoku.api.Factories.Concrete;

import com.sudoku.api.Factories.Abstract.SudokuFactory;
import com.sudoku.api.Models.DAO.SudokuDAO;

public class SudokuResolverFactory implements SudokuFactory {
    @Override
    public SudokuDAO createBoard() {
        return new SudokuDAO();
    }

    @Override
    public SudokuDAO createPuzzle(SudokuDAO sudoku) {
        return sudoku;
    }
}
