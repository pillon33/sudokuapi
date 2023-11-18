package com.sudoku.api.Factories.Concrete;

import com.sudoku.api.Factories.Abstract.SudokuFactory;
import com.sudoku.api.Models.DAO.SudokuDAO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseSudokuFactory implements SudokuFactory {
    private String type;
    public static final List<Integer> BOARD_WITH_DIAGONAL_VALUES = List.of(
    1, 2, 3, 0, 0, 0, 0, 0, 0,
            4, 5, 6, 0, 0, 0, 0, 0, 0,
            7, 8, 9, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 1, 2, 3, 0, 0, 0,
            0, 0, 0, 4, 5, 6, 0, 0, 0,
            0, 0, 0, 7, 8, 9, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 1, 2, 3,
            0, 0, 0, 0, 0, 0, 4, 5, 6,
            0, 0, 0, 0, 0, 0, 7, 8, 9
    );

    public static final List<Integer> BOARD_WITH_DEFAULT_VALUES = List.of(
    1, 2, 3, 4, 5, 6, 7, 8, 9,
            4, 5, 6, 7, 8, 9, 1, 2, 3,
            7, 8, 9, 1, 2, 3, 4, 5, 6,
            2, 3, 4, 5, 6, 7, 8, 9, 1,
            5, 6, 7, 8, 9, 1, 2, 3, 4,
            8, 9, 1, 2, 3, 4, 5, 6, 7,
            3, 4, 5, 6, 7, 8, 9, 1, 2,
            6, 7, 8, 9, 1, 2, 3, 4, 5,
            9, 1, 2, 3, 4, 5, 6, 7, 8
    );

    public BaseSudokuFactory() {
        this.type = "";
    }

    @Override
    public SudokuDAO createBoard() {
        switch (type) {
            case "diagonal" -> {
                return new SudokuDAO(BOARD_WITH_DIAGONAL_VALUES);
            }
            case "default" -> {
                return new SudokuDAO(BOARD_WITH_DEFAULT_VALUES);
            }
            default -> {
                return new SudokuDAO();
            }
        }
    }

    @Override
    public SudokuDAO createPuzzle(SudokuDAO sudoku) {
        return sudoku;
    }
}
