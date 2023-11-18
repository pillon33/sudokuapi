package com.sudoku.api.Resolvers;

import com.sudoku.api.Models.DAO.SudokuDAO;

import java.util.HashMap;
import java.util.List;

public interface Resolver {
    /**
     * Finds one solution for given puzzle, returns null if solution couldn't be found.
     * @param sudoku puzzle to resolve
     * @return solution for given puzzle, null if solution couldn't be found
     */
    public List<Integer> resolve(SudokuDAO sudoku);

    /**
     * Checks if given puzzle has exactly one solution
     * @param sudoku puzzle
     * @return Does the puzzle have any solution
     */
    public Boolean isPuzzle(SudokuDAO sudoku);

    /**
     * Checks how much solutions there are for given puzzle
     * @param sudoku puzzle
     * @return Returns number of solutions that the resolver could find
     */
    public int countSolutions(SudokuDAO sudoku);

    /**
     * Returns list of moves that the resolver makes to find solution.
     * @param sudoku puzzle
     * @return list of moves that the resolver makes to find the solution ( field : insertedValue )
     */
    public List<HashMap<String, Integer>> getMoves(SudokuDAO sudoku);
}
