package com.sudoku.api.Models.DAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Log4j2
public class SudokuBlockDAO {
    List<SudokuCellDAO> cells;

    /**
     * Initializes Sudoku block containing 9 empty cells.
     */
    public SudokuBlockDAO() {
        this.cells = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            SudokuCellDAO cell = new SudokuCellDAO(0, 0, false);
            this.cells.add(cell);
        }
    }

    /**
     * Initializes Sudoku block containing values in given List.
     * @param cells
     */
    public SudokuBlockDAO(List<Integer> cells) {
        this.cells = new ArrayList<>();

        if (cells.size() != 9) {
            throw new RuntimeException("Wrong List size.");
        }

        for (Integer i : cells) {
            SudokuCellDAO cell = new SudokuCellDAO(i, i, false);
            this.cells.add(cell);
        }
    }

    public SudokuCellDAO getCellAt(int row, int col) {
        int idx = getIdxFromCoordinates(row, col);
        this.checkRange(idx);
        return this.cells.get(idx);
    }

    public void setCellAt(int row, int col, SudokuCellDAO cell) {
        int idx = getIdxFromCoordinates(row, col);
        this.checkRange(idx);
        this.cells.set(idx, cell);
    }

    public List<SudokuCellDAO> getRow(int row) {
        List<SudokuCellDAO> result = new ArrayList<>();
        for (int col = 0; col < 3; col++) {
            result.add(this.getCellAt(row, col));
        }
        return result;
    }

    public List<SudokuCellDAO> getCol(int col) {
        List<SudokuCellDAO> result = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            result.add(this.getCellAt(row, col));
        }
        return result;
    }

    private int getIdxFromCoordinates(int row, int col) {
        return row*3 + col;
    }

    private void checkRange(int idx) {
        if (idx < 0 || idx > 8) {
            throw new RuntimeException(String.format("Index out of range: %s", idx));
        }
    }
}
