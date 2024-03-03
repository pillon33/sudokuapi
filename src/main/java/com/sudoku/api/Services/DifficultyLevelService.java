package com.sudoku.api.Services;

import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.DTO.DifficultyData;
import com.sudoku.api.Models.DTO.SudokuDTO;
import com.sudoku.api.Resolvers.BacktrackingResolver;
import com.sudoku.api.Resolvers.OptimisedBacktrackingResolver;
import com.sudoku.api.Resolvers.Resolver;

public class DifficultyLevelService {
    public static Double getDifficultyLevelForBoard(SudokuDAO sudoku) {
        var optimisedResolver = new OptimisedBacktrackingResolver();

        var optimisedResult = optimisedResolver.getMoves(sudoku).size();

        var numberOfHiddenFields = SudokuService.getNumberOfNonClues(sudoku);

        double result = (optimisedResult - numberOfHiddenFields);
        result /= 4;

        double hiddenFieldsResult = numberOfHiddenFields - 25;
        hiddenFieldsResult /= 64;
        hiddenFieldsResult *= 100;

        hiddenFieldsResult = DifficultyLevelService.capResult(hiddenFieldsResult);
        result = DifficultyLevelService.capResult(result);

        result = (result + hiddenFieldsResult)/2;

        result *= 100;
        result = (int) result;
        result /= 100;

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

    private static Double capResult(Double result) {
        result = result < 0 ? 0 : result;
        result = result > 100 ? 100 : result;

        return result;
    }
}
