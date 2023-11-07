package com.inzynierka.sudokuapi.Resolvers;

import com.inzynierka.sudokuapi.Models.DAO.SudokuDAO;

import java.util.HashMap;
import java.util.List;

public class BacktrackingResolver implements Resolver {
    @Override
    public List<Integer> resolve(SudokuDAO sudoku) {
        int idx = 0;
        int firstFaceDownIdx = 0;

        while (sudoku.isHint(idx)) {
            idx++;
        }

        firstFaceDownIdx = idx;

        while(!sudoku.isSolved()) {

        }

        return sudoku.getValues();
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
