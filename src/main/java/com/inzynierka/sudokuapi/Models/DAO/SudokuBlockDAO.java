package com.inzynierka.sudokuapi.Models.DAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Log4j2
public class SudokuBlockDAO {
    List<SudokuCellDAO> cells;

    /**
     * Initializes Sudoku block containing 9 empty cells.
     */
    SudokuBlockDAO() {
        this.cells = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            SudokuCellDAO cell = new SudokuCellDAO(0, 0, false);
            this.cells.add(cell);
        }
    }

    public SudokuCellDAO getCellAt(int row, int col) {
        int idx = getIdxFromCoordinates(row, col);
        this.checkRange(idx);
        return this.cells.get(idx);
    }

    public Boolean isCorrect() {
        ArrayList<Integer> values = new ArrayList<>(Arrays.asList(
                0, 0, 0,
                0, 0, 0,
                0, 0, 0
        ));

        for (SudokuCellDAO cell : this.cells) {
            int value = cell.getValue();

            if (value == 0) continue;

            value--;

            if (values.get(value).equals(1)) return false;

            values.set(value, 1);
        }

        return true;
    }

    public List<SudokuCellDAO> getRow(int row) {
        for (int col = 0; col < 3; col++)
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
