package com.sudoku.api.Controllers;

import com.sudoku.api.Factories.Concrete.SudokuResolverFactory;
import com.sudoku.api.Models.DAO.SudokuCellDAO;
import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.DTO.SudokuDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
        SudokuResolverFactory resolverFactory = new SudokuResolverFactory();
        SudokuDAO sudoku = resolverFactory.create();
        log.info(String.format("\n %s", sudoku.toString()));
        return new ResponseEntity<>(sudoku, HttpStatus.OK);
    }

    @GetMapping("/diagonal")
    public ResponseEntity<Object> getDiagonalSudokuPuzzle() {
        SudokuResolverFactory resolverFactory = new SudokuResolverFactory();
        SudokuDAO sudoku = resolverFactory.create();
        log.info(String.format("\n %s", sudoku.toString()));
        return new ResponseEntity<>(sudoku, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        SudokuDAO sudoku = new SudokuDAO();
        SudokuCellDAO cell = sudoku.getCellAtPosition(5, 8);
        cell.setSolution(8);
        cell.setValue(8);
        sudoku.setCellAtPosition(5, 8, cell);
        log.info(String.format("\n %s", sudoku.toString()));
        return new ResponseEntity<>(SudokuDTO.fromSudokuDAO(sudoku), HttpStatus.OK);
    }
}
