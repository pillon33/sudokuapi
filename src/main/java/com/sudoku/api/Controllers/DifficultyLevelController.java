package com.sudoku.api.Controllers;

import com.sudoku.api.Factories.Concrete.SudokuResolverFactory;
import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.DTO.DifficultyData;
import com.sudoku.api.Resolvers.BacktrackingResolver;
import com.sudoku.api.Resolvers.OptimisedBacktrackingResolver;
import com.sudoku.api.Services.DifficultyLevelService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("sudoku-api/difficulty-level")
@CrossOrigin
@Log4j2
public class DifficultyLevelController {
    public ResponseEntity<Object> getDifficultyParamsForBacktracking(@RequestBody String data) {
        String[] arr = data.split("\r\n");
        var result = new ArrayList<DifficultyData>();

        for (var item : arr) {
            String[] numbers = item.split("");
            var sudokuArray = new ArrayList<Integer>();
            for (var n : numbers) {
                Integer number;
                if (n.equals(".")) {
                    number = 0;
                } else {
                    number = Integer.valueOf(n);
                }
                sudokuArray.add(number);
            }
            var sudoku = new SudokuDAO(sudokuArray);
            var difficultyData = DifficultyLevelService.getDifficultyData(sudoku, new BacktrackingResolver());
            result.add(difficultyData);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/getBacktrackingDifficultyParams")
    public ResponseEntity<Object> getDifficultyParamsForOptimised(@RequestBody String data) {
        String[] arr = data.split("\r\n");
        var result = new ArrayList<DifficultyData>();

        for (var item : arr) {
            String[] numbers = item.split("");
            var sudokuArray = new ArrayList<Integer>();
            for (var n : numbers) {
                Integer number;
                if (n.equals(".")) {
                    number = 0;
                } else {
                    number = Integer.valueOf(n);
                }
                sudokuArray.add(number);
            }
            var sudoku = new SudokuDAO(sudokuArray);
            var difficultyData = DifficultyLevelService.getDifficultyData(sudoku, new OptimisedBacktrackingResolver());
            result.add(difficultyData);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/getDifficultyStats")
    public ResponseEntity<Object> getDifficultyStats(@RequestBody String data) {
        String[] arr = data.split("\r\n");
        var result = new ArrayList<Double>();

        for (var item : arr) {
            String[] numbers = item.split("");
            var sudokuArray = new ArrayList<Integer>();
            for (var n : numbers) {
                Integer number;
                if (n.equals(".")) {
                    number = 0;
                } else {
                    number = Integer.valueOf(n);
                }
                sudokuArray.add(number);
            }
            var sudoku = new SudokuDAO(sudokuArray);
            var difficultyData = DifficultyLevelService.getDifficultyLevelForBoard(sudoku);
            result.add(difficultyData);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getDifficultyStatistics")
    public ResponseEntity<Object> getDifficultyStatistics() {
        var result = new ArrayList<Double>();
        for (int i = 0; i < 1000; i++) {
            SudokuResolverFactory sf = new SudokuResolverFactory();
            sf.setResolver(new OptimisedBacktrackingResolver());
            sf.setNumberOfHiddenFields(60);
            SudokuDAO sudoku = sf.create();
            result.add(DifficultyLevelService.getDifficultyLevelForBoard(sudoku));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
