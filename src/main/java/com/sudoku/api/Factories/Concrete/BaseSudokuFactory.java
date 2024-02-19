package com.sudoku.api.Factories.Concrete;

import com.sudoku.api.Factories.Abstract.SudokuFactory;
import com.sudoku.api.Models.DAO.SudokuCellDAO;
import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.Enums.BaseSudokuType;
import com.sudoku.api.Services.SudokuService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Log4j2
@Getter
@Setter
public class BaseSudokuFactory implements SudokuFactory {
    private BaseSudokuType type;
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

    }

    private SudokuDAO getBoardWithRandomDiagonalValues() {
        Random random = new Random();
        List<Integer> result = new ArrayList<Integer>(Collections.nCopies(81, 0));
        SudokuDAO sudoku = new SudokuDAO(result);

        for (int i = 0; i < 3; i++) {
            List<Integer> numbers = new ArrayList<>();
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    int cellR = r + 3*i;
                    int cellC = c + 3*i;

                    Integer n = random.nextInt(9) + 1;

                    while (numbers.contains(n)) {
                        n = random.nextInt(9) + 1;
                    }

                    numbers.add(n);

                    SudokuCellDAO cell = new SudokuCellDAO(n, n, true);

                    sudoku.setCellAtPosition(cellR, cellC, cell);
                }
            }
        }

        return sudoku;
    }

    @Override
    public SudokuDAO createBoard() {
        switch (type) {
            case BASE_DIAGONAL -> {
                return new SudokuDAO(BOARD_WITH_DIAGONAL_VALUES);
            }
            case BASE_WITH_DEFAULT_VALUES -> {
                return new SudokuDAO(BOARD_WITH_DEFAULT_VALUES);
            }
            case RANDOM_DIAGONAL -> {
                return new SudokuDAO(this.getBoardWithRandomDiagonalValues());
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
