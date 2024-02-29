package com.sudoku.api.Services;

import com.sudoku.api.Models.DAO.SudokuCellDAO;
import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.Other.BacktrackingData;
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

    /**
     * Translates given coordinates into idx in 1 dimensional array.
     * @param row
     * @param col
     * @return
     */
    public static int getCellIdxFromCoordinates(int row, int col) {
        return row*9 + col;
    }

    /**
     * Removes illegal candidates for each cell on board.
     * @param sudoku
     * @return
     */
    public static SudokuDAO removeIllegalCandidatesForBoard(SudokuDAO sudoku) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SudokuCellDAO cell = sudoku.getCellAtPosition(row, col);

                SudokuService.removeIllegalCandidatesForCell(sudoku, row, col);
            }
        }

        return sudoku;
    }

    /**
     * Removes  illegal candidates for given cell.
     * @param sudoku
     * @param row
     * @param col
     * @return
     */
    public static List<Integer> removeIllegalCandidatesForCell(SudokuDAO sudoku, int row, int col) {
        SudokuCellDAO cell = sudoku.getCellAtPosition(row, col);

        if (cell.getIsClue()) {
            return cell.getCandidates();
        }

        var candidates = new ArrayList<>(cell.getCandidates());

        int initialValue = cell.getValue();

        for (var candidate : candidates) {
            cell.setValue(candidate);

            if (!sudoku.isCorrectOptimisedForLastMove(row, col)) {
                cell.removeCandidate(cell.getValue());
            }
        }

        cell.setValue(initialValue);

        return cell.getCandidates();
    }

    public static List<BacktrackingData> getEmptyCellCoordinates(SudokuDAO sudoku) {
        var result = new ArrayList<BacktrackingData>();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SudokuCellDAO cell = sudoku.getCellAtPosition(row, col);

                if (!cell.getIsClue() & cell.getValue() == 0) {
                    var coordinates = new BacktrackingData();
                    coordinates.setCol(col);
                    coordinates.setRow(row);
                    result.add(coordinates);
                }
            }
        }

        return result;
    }

    public static HashMap<String, Integer> getCellCoordinatesWithLeastCandidates(SudokuDAO sudoku) {
        int leastNumberOfCandidates = 10;
        int resultRow = -1;
        int resultCol = -1;

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SudokuCellDAO cell = sudoku.getCellAtPosition(row, col);

                if (cell.getIsClue() || cell.getValue() != 0) {
                    continue;
                }

                int numberOfCandidates = cell.getNumberOfCandidates();

                if (numberOfCandidates < leastNumberOfCandidates) {
                    leastNumberOfCandidates = numberOfCandidates;
                    resultRow = row;
                    resultCol = col;
                }
            }
        }

        HashMap<String, Integer> coordinates = new HashMap<>();

        coordinates.put("row", resultRow);
        coordinates.put("col", resultCol);
        return coordinates;
    }
}
