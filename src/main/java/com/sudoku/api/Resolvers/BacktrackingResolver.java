package com.sudoku.api.Resolvers;

import com.sudoku.api.Models.DAO.SudokuDAO;

import java.util.HashMap;
import java.util.List;

public class BacktrackingResolver implements Resolver {
    @Override
    public List<Integer> resolve(SudokuDAO sudoku) {
        return null;
    }

    @Override
    public Boolean isPuzzle(SudokuDAO sudoku) {
        return null;
    }

    @Override
    public int countSolutions(SudokuDAO sudoku) {
        return 0;
    }

    @Override
    public List<HashMap<String, Integer>> getMoves(SudokuDAO sudoku) {
        return null;
    }
}
