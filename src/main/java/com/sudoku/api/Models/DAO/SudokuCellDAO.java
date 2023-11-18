package com.sudoku.api.Models.DAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SudokuCellDAO {
    /**
     * Value currently inserted in this cell.
     */
    private Integer value;

    /**
     * Correct value that should be inserted in this cell.
     */
    private Integer solution;

    /**
     * Tells if this cell is a clue.
     */
    private Boolean isClue;

    /**
     * Potential values for this cell.
     */
    private List<Integer> candidates;

    /**
     * Initializes SudokuCell with default candidates list (all digits).
     * @param value
     * @param solution
     * @param isClue
     */
    public SudokuCellDAO(Integer value, Integer solution, Boolean isClue) {
        this.value = value;
        this.solution = solution;
        this.isClue = isClue;
        this.candidates = new ArrayList<>();
        this.setDefaultCandidates();
    }

    /**
     * Initializes a clue SudokuCell.
     * @param solution
     */
    public SudokuCellDAO(Integer solution) {
        this.value = solution;
        this.solution = solution;
        this.isClue = true;
        this.candidates = new ArrayList<>();
        this.candidates.add(solution);
    }

    public Boolean isCorrect() {
        return this.value.equals(this.solution) && !this.value.equals(0);
    }

    public void addCandidate(Integer candidate) {
        if (!this.candidates.contains(candidate)) {
            this.candidates.add(candidate);
        }
    }

    public void removeCandidate(Integer candidate) {
        this.candidates.remove(candidate);
    }

    public int getNumberOfCandidates() {
        return this.candidates.size();
    }

    public void setDefaultCandidates() {
        this.candidates.clear();

        for (int i = 1; i <= 9; i++) {
            this.candidates.add(i);
        }
    }
}
