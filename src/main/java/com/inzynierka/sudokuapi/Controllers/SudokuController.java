package com.inzynierka.sudokuapi.Controllers;

import com.inzynierka.sudokuapi.Models.DAO.SudokuDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sudoku-api")
@Log4j2
public class SudokuController {

    @GetMapping("/base")
    public ResponseEntity<Object> getBaseSudokuPuzzle() {
        SudokuDAO sudoku = SudokuDAO.getBaseBoard();
        log.info(String.format("\n %s", sudoku.toString()));
        return new ResponseEntity<>(sudoku, HttpStatus.OK);
    }

    @GetMapping("/diagonal")
    public ResponseEntity<Object> getDiagonalSudokuPuzzle() {
        SudokuDAO sudoku = SudokuDAO.getBoardWithDiagonalValues();
        log.info(String.format("\n %s", sudoku.toString()));
        return new ResponseEntity<>(sudoku, HttpStatus.OK);
    }
}
