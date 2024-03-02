package com.sudoku.api.Services;

import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.DTO.DifficultyData;
import com.sudoku.api.Models.DTO.SudokuDTO;
import com.sudoku.api.Resolvers.BacktrackingResolver;
import com.sudoku.api.Resolvers.OptimisedBacktrackingResolver;
import com.sudoku.api.Resolvers.Resolver;

public class DifficultyLevelService {
    public static Double getDifficultyLevelForBoard(SudokuDAO sudoku) {
        var backtrackingResolver = new BacktrackingResolver();
        var optimisedResolver = new OptimisedBacktrackingResolver();

        var backtrackingResult = backtrackingResolver.getMoves(sudoku).size();
        var optimisedResult = optimisedResolver.getMoves(sudoku).size();

        double result = (backtrackingResult + optimisedResult);
        result = result/2;

        return result;
    }

    public static DifficultyData getDifficultyData(SudokuDAO sudoku, Resolver resolver) {
        var result = new DifficultyData();

        var data = resolver.getMoves(sudoku);

        result.setNumberOfMoves((long) data.size());

        result.setNumberOfMistakes(
                data
                        .stream()
                        .filter(
                                element -> element.getInsertedValue().equals(0)
                        )
                        .count()
        );

        return result;
    }
}
