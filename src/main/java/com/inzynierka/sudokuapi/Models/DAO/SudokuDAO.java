package com.inzynierka.sudokuapi.Models.DAO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@Log4j2
public class SudokuDAO {
    private List<Integer> values;
    private List<Integer> solution;
    private List<Boolean> mask;

    public SudokuDAO() {
        this.solution = new ArrayList<>();
        this.values = new ArrayList<>();
        this.mask = new ArrayList<>();

        for (int i = 0; i < 81; i++) {
            this.solution.add(0);
            this.mask.add(false);
        }

        this.fillValues();
    }

    public void fillValues() {
        int i = 0;
        this.values.clear();
        for (Integer value : this.solution) {
            if (mask.get(i++)) {
                this.values.add(value);
            } else {
                this.values.add(0);
            }
        }
    }

    public static SudokuDAO getBoardWithDiagonalValues() {
        SudokuDAO sudoku = new SudokuDAO();
        Random random = new Random();

        for(int square = 0; square < 3; square++) {
            List<Integer> usedValues = new ArrayList<>();
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int r = square*3 + row + 1;
                    int c = square*3 + col + 1;
                    int value = random.nextInt(1, 10);
                    while (usedValues.contains(value)) {
                        value = random.nextInt(1, 10);
                    }
                    usedValues.add(value);
                    sudoku.setValue(r, c, value, true);
                }
            }
        }

        sudoku.fillValues();

        return sudoku;
    }

    public static SudokuDAO getBaseBoard() {
        SudokuDAO sudoku = new SudokuDAO();

        List<Integer> board = new ArrayList<>(Arrays.asList(
                1, 2, 3, 4, 5, 6, 7, 8, 9,
                4, 5, 6, 7, 8, 9, 1, 2, 3,
                7, 8, 9, 1, 2, 3, 4, 5, 6,
                2, 3, 4, 5, 6, 7, 8, 9, 1,
                5, 6, 7, 8, 9, 1, 2, 3, 4,
                8, 9, 1, 2, 3, 4, 5, 6, 7,
                3, 4, 5, 6, 7, 8, 9, 1, 2,
                6, 7, 8, 9, 1, 2, 3, 4, 5,
                9, 1, 2, 3, 4, 5, 6, 7, 8
                ));
        List<Boolean> mask = new ArrayList<>(Arrays.asList(
                true, true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true
        ));

        sudoku.setSolution(board);
        sudoku.setMask(mask);
        sudoku.fillValues();
        return sudoku;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("\t");
        // print col numbers
        for (int i=0; i < 9; i++) {
            result.append(i + 1);
            result.append("\t");
        }

        result.append("\n");

        for (int i=0; i < 9; i++) {
            result.append(i + 1);
            result.append("\t");
            for (int j=0; j < 9; j++) {
                result.append(getValue(i + 1, j + 1));
                result.append("\t");
            }
            result.append("\n");
        }

        return result.toString();
    }

    public void setValue (int row, int col, int value) {
        if (!isInRange(row) || !isInRange(col)) {
            log.error(String.format("One of indexes out of range: %s\t%s", row, col));
            throw new RuntimeException("One of indexes out of range");
        }

        int idx = getIdx(row, col);
        this.values.set(idx, value);
    }

    public Boolean isHint(int row, int col) {
        if (!isInRange(row) || !isInRange(col)) {
            log.error(String.format("One of indexes out of range: %s\t%s", row, col));
            throw new RuntimeException("One of indexes out of range");
        }

        int idx = this.getIdx(row, col);
        return this.mask.get(idx);
    }

    public Boolean isHint(int idx) {
        if (idx < 0 || idx >= 81) {
            log.error(String.format("Index out of range: %s", idx));
            throw new RuntimeException("Index out of range");
        }

        return this.mask.get(idx);
    }

    public void setValue (int row, int col, int value, Boolean visible) {
        if (!isInRange(row) || !isInRange(col)) {
            log.error(String.format("One of indexes out of range: %s\t%s", row, col));
            throw new RuntimeException("One of indexes out of range");
        }

        int idx = getIdx(row, col);
        this.values.set(idx, value);
        this.mask.set(idx, visible);
    }

    public void setValue (int row, int col, Boolean visible) {
        if (!isInRange(row) || !isInRange(col)) {
            log.error(String.format("One of indexes out of range: %s\t%s", row, col));
            throw new RuntimeException("One of indexes out of range");
        }

        int idx = getIdx(row, col);
        this.mask.set(idx, visible);
    }

    public Integer getValue(int row, int col) {
        if (!isInRange(row) || !isInRange(col)) {
            log.error(String.format("One of indexes out of range: %s\t%s", row, col));
            throw new RuntimeException("One of indexes out of range");
        }

        int idx = getIdx(row, col);
        return this.values.get(idx);
    }

    public Integer getValue(int idx) {
        if (idx < 0 || idx >= 81) {
            log.error(String.format("Index out of range: %s", idx));
            throw new RuntimeException("Index out of range");
        }

        return this.values.get(idx);
    }

    private int getIdx(int row, int col) {
        return (row - 1) * 9 + col - 1;
    }

    private Boolean isInRange(int idx) {
        return idx <= 9 && idx > 0;
    }

    private Boolean isInSquareRange(int idx) {
        return idx <= 3 && idx > 0;
    }

    public List<Integer> getRow(int row) {
        if (!isInRange(row)) {
            log.error(String.format("Index out of range: %s", row));
            throw new RuntimeException("One of indexes out of range");
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            result.add(getValue(row, i));
        }

        return result;
    }

    public List<Integer> getCol(int col) {
        if (!isInRange(col)) {
            log.error(String.format("Index out of range: %s", col));
            throw new RuntimeException("One of indexes out of range");
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            result.add(getValue(i, col));
        }

        return result;
    }

    public List<Integer> getSquare(int row, int col) {
        if (!isInSquareRange(row) || !isInSquareRange(col)) {
            log.error(String.format("One of indexes out of range: %s\t%s", row, col));
            throw new RuntimeException("One of indexes out of range");
        }

        List<Integer> result = new ArrayList<>();

        for (int r = 1; r <= 3; r++) {
            for (int c = 1; c <= 3; c++) {
                result.add(getValue((row - 1)*3 + r, (col - 1)*3 + c));
            }
        }

        return result;
    }

    public Boolean isCorrect() {
        for (int r = 1; r <= 3; r++) {
            for (int c = 1; c <= 3; c++) {
                List<Integer> row = getRow(r*c);
                List<Integer> col = getCol(r*c);
                List<Integer> sq = getSquare(r, c);

                Boolean isRowCorrect = containsUniqueNumbers(row);
                Boolean isColCorrect = containsUniqueNumbers(col);
                Boolean isSquareCorrect = containsUniqueNumbers(sq);

                if (!isRowCorrect || !isColCorrect || !isSquareCorrect) {
                    return false;
                }
            }
        }

        return true;
    }

    public Boolean hasAllValues() {
        return !this.values.contains(0);
    }

    public Boolean isSolved() {
        return this.hasAllValues() && this.isCorrect();
    }

    private Boolean containsUniqueNumbers(List<Integer> list) {
        if (list.size() != 9) {
            log.error(String.format("Wrong list size: %s", list.size()));
            throw new RuntimeException("Wrong list size");
        }

        List<Integer> numbers = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        int i = 0;

        for (Integer number : list) {
            numbers.set(number, numbers.get(number) + 1);

            if (number != 0 && numbers.get(number) > 1) {
                return false;
            }
        }

        return true;
    }

}
