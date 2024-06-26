package com.sudoku.api.Resolvers;

import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.DTO.ResolverMoveDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public interface Resolver {

    /**
     * Finds one solution for given puzzle, returns null if solution couldn't be found.
     * @param sudoku puzzle to resolve
     * @return solvedBoard, null if solution couldn't be found
     */
    public SudokuDAO resolve(SudokuDAO sudoku);

    /**
     * Checks if given puzzle has exactly one solution
     * @param sudoku puzzle
     * @return Does the puzzle have any solution
     */
    public Boolean isPuzzle(SudokuDAO sudoku);

    /**
     * Checks how many solutions there are for given puzzle
     * @param sudoku puzzle
     * @return Returns number of solutions that the resolver could find
     */
    public int countSolutions(SudokuDAO sudoku);

    /**
     * Returns list of moves that the resolver makes to find solution.
     * @param sudoku puzzle
     * @return list of moves that the resolver makes to find the solution ( field : insertedValue )
     */
    public List<ResolverMoveDTO> getMoves(SudokuDAO sudoku);
}
