package com.sudoku.api.Models.DAO;

import com.sudoku.api.Services.SudokuService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Getter
@Setter
@Log4j2
public class SudokuDAO {
    private List<SudokuBlockDAO> blocks;

    /**
     * Creates sudoku board with all empty cells
     */
    public SudokuDAO() {
        this.blocks = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            SudokuBlockDAO block = new SudokuBlockDAO();
            this.blocks.add(block);
        }
    }

    /**
     * Creates sudoku board containing values in given List.
     * @param cells
     */
    public SudokuDAO(List<Integer> cells) {
        this.blocks = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            SudokuBlockDAO block = new SudokuBlockDAO();
            this.blocks.add(block);
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Integer value = cells.get(row*9 + col);
                SudokuCellDAO cell = new SudokuCellDAO(value, value, false);
                this.setCellAtPosition(row, col, cell);
            }
        }
    }

    public SudokuCellDAO getCellAtPosition(int row, int col) {
        int blockIdx = SudokuService.getBlockIdxContiningCellAtPosition(row, col);

        checkRange(blockIdx);

        SudokuBlockDAO block = this.blocks.get(blockIdx);

        HashMap<String, Integer> cellCoordinatesInsideBlock = SudokuService.getBlockCoordinatesForCellAtPosition(row, col);

        return block.getCellAt(cellCoordinatesInsideBlock.get("row"), cellCoordinatesInsideBlock.get("col"));
    }

    public void setCellAtPosition(int row, int col, SudokuCellDAO cell) {
        int blockIdx = SudokuService.getBlockIdxContiningCellAtPosition(row, col);

        checkRange(blockIdx);

        SudokuBlockDAO block = this.blocks.get(blockIdx);

        HashMap<String, Integer> cellCoordinatesInsideBlock = SudokuService.getBlockCoordinatesForCellAtPosition(row, col);

        block.setCellAt(cellCoordinatesInsideBlock.get("row"), cellCoordinatesInsideBlock.get("col"), cell);

        this.blocks.set(blockIdx, block);
    }

    private void checkRange(int idx) {
        if (idx < 0 || idx > 8) {
            throw new RuntimeException(String.format("Index out of range: %s", idx));
        }
    }

    public List<SudokuCellDAO> getRow(int row) {
        SudokuBlockDAO block = null;
        List<SudokuCellDAO> cells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            block = this.blocks.get(SudokuService.getBlockIdxContiningCellAtPosition(row, i*3));
            cells.addAll(block.getRow(row%3));
        }
        return cells;
    }

    public List<SudokuCellDAO> getCol(int col) {
        SudokuBlockDAO block = null;
        List<SudokuCellDAO> cells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            block = this.blocks.get(SudokuService.getBlockIdxContiningCellAtPosition(i*3, col));
            cells.addAll(block.getCol(col%3));
        }
        return cells;
    }

    public String toString() {
        String result = "";
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (row == 0) {
                    result += col == 0 ? "" : "\t" + col;
                    continue;
                }

                if (col == 0) {
                    result += Integer.toString(row);
                    continue;
                }

                result += "\t" + getCellAtPosition(row - 1, col - 1).getValue();
            }
            result += "\n";
        }
        return result;
    }
}
