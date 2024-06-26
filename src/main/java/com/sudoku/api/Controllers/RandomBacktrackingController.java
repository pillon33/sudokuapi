package com.sudoku.api.Controllers;

import com.sudoku.api.Factories.Concrete.SudokuResolverFactory;
import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.DTO.ResolverMoveDTO;
import com.sudoku.api.Models.DTO.SudokuDTO;
import com.sudoku.api.Resolvers.OptimisedBacktrackingResolver;
import com.sudoku.api.Resolvers.RandomBacktrackingResolver;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sudoku-api/random-backtracking")
@CrossOrigin
@Log4j2
public class RandomBacktrackingController {
    /**
     * Returns a puzzle created by backtracking resolver.
     * @param numberOfFields number of fields that are hidden.
     * @return
     */
    @GetMapping("/getPuzzle")
    public ResponseEntity<Object> getBoardFromResolverFactory(@RequestParam("numberOfFields") Integer numberOfFields) {
        SudokuResolverFactory sf = new SudokuResolverFactory();
        sf.setResolver(new OptimisedBacktrackingResolver());
        sf.setNumberOfHiddenFields(numberOfFields);
        SudokuDAO sudoku = sf.create();
        return new ResponseEntity<>(SudokuDTO.fromSudokuDAO(sudoku), HttpStatus.OK);
    }

    /**
     * Returns a list of moves that resolver made to solve given puzzle.
     * @param sudoku puzzle to solve
     * @return
     */
    @PostMapping("/getMoves")
    public ResponseEntity<Object> getMovesFromResolver(@RequestBody SudokuDTO sudoku) {
        RandomBacktrackingResolver resolver = new RandomBacktrackingResolver();
        List<ResolverMoveDTO> moves = resolver.getMoves(SudokuDAO.fromDTO(sudoku));

        return new ResponseEntity<>(moves, HttpStatus.OK);
    }
}
