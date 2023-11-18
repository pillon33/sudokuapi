package com.sudoku.api.Services;

import com.sudoku.api.Models.DAO.SudokuCellDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SudokuService {
    /**
     * Checks if given group of cells - block, row or column contains unique digits.
     * @param group
     * @return isCorrect
     */
    public static Boolean isGroupCorrect(List<SudokuCellDAO> group) {
        ArrayList<Integer> values = new ArrayList<>();

        for (SudokuCellDAO cell : group) {
            int value = cell.getValue();

            if (value == 0) continue;

            if (values.contains(value)) return false;

            values.add(value);
        }

        return true;
    }

    /**
     * Returns idx of block containing field with given coordinates
     * @param row
     * @param col
     * @return
     */
    public static int getBlockIdxContiningCellAtPosition(int row, int col) {
        return ((int) row/3)*3 + (int) col/3;
    }

    /**
     * Translates given coordinates into coordinates inside block.
     * @param row
     * @param col
     * @return
     */
    public static HashMap<String, Integer> getBlockCoordinatesForCellAtPosition(int row, int col) {
        HashMap<String, Integer> coordinates = new HashMap<>();
        int newRow = row%3;
        int newCol = col%3;

        coordinates.put("row", newRow);
        coordinates.put("col", newCol);
        return coordinates;
    }
}
