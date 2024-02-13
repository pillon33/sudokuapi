package com.sudoku.api.Controllers;

import com.sudoku.api.Factories.Concrete.BaseSudokuFactory;
import com.sudoku.api.Factories.Concrete.SudokuResolverFactory;
import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.DTO.SudokuDTO;
import com.sudoku.api.Resolvers.BacktrackingResolver;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sudoku-api")
@CrossOrigin
@Log4j2
public class SudokuController {

    @GetMapping("/default")
    public ResponseEntity<Object> getBaseSudokuPuzzle() {
        BaseSudokuFactory sf = new BaseSudokuFactory();
        sf.setType("default");
        SudokuDAO sudoku = sf.create();
        log.info(String.format("\n %s", sudoku.toString()));
        return new ResponseEntity<>(SudokuDTO.fromSudokuDAO(sudoku), HttpStatus.OK);
    }

    @GetMapping("/diagonal")
    public ResponseEntity<Object> getDiagonalSudokuPuzzle() {
        BaseSudokuFactory sf = new BaseSudokuFactory();
        sf.setType("diagonal");
        SudokuDAO sudoku = sf.create();
        log.info(String.format("\n %s", sudoku.toString()));
        return new ResponseEntity<>(SudokuDTO.fromSudokuDAO(sudoku), HttpStatus.OK);
    }

    @GetMapping("/resolver")
    public ResponseEntity<Object> getBoardFromResolverFactory() {
        SudokuResolverFactory sf = new SudokuResolverFactory();
        sf.setResolver(new BacktrackingResolver());
        SudokuDAO sudoku = sf.create();
        log.info(String.format("\n %s", sudoku));
        return new ResponseEntity<>(SudokuDTO.fromSudokuDAO(sudoku), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        BaseSudokuFactory sf = new BaseSudokuFactory();
        sf.setType("diagonal");
        SudokuDAO sudoku = sf.create();
        log.info(String.format("\n %s", sudoku.toString()));
        return new ResponseEntity<>(SudokuDTO.fromSudokuDAO(sudoku), HttpStatus.OK);
    }
}
