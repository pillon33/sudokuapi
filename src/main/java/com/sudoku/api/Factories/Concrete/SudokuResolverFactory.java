package com.sudoku.api.Factories.Concrete;

import com.sudoku.api.Factories.Abstract.SudokuFactory;
import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Resolvers.Resolver;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SudokuResolverFactory implements SudokuFactory {
    private Resolver resolver;

    @Override
    public SudokuDAO createBoard() {
        return resolver.resolve(new SudokuDAO(BaseSudokuFactory.BOARD_WITH_DIAGONAL_VALUES));
    }

    @Override
    public SudokuDAO createPuzzle(SudokuDAO sudoku) {
        return sudoku;
    }
}
