package com.sudoku.api.Models.DTO;

import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Services.DifficultyLevelService;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SudokuDTO {
    public final List<Integer> cells;
    public final List<Boolean> mask;

    public final Double score;

    public SudokuDTO(List<Integer> cells, List<Boolean> mask, Double score) {
        this.cells = cells;
        this.mask = mask;
        this.score = score;
    }

    public static SudokuDTO fromSudokuDAO(SudokuDAO sudoku) {
        List<Integer> cells = new ArrayList<>();
        List<Boolean> mask = new ArrayList<>();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells.add(sudoku.getCellAtPosition(row, col).getSolution());
                mask.add(sudoku.getCellAtPosition(row, col).getIsClue());
            }
        }

        Double score = DifficultyLevelService.getDifficultyLevelForBoard(sudoku);

        return new SudokuDTO(cells, mask, score);
    }
}
