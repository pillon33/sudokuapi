package com.sudoku.api.Controllers;

import com.sudoku.api.Factories.Concrete.SudokuResolverFactory;
import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.DTO.ResolverMoveDTO;
import com.sudoku.api.Models.DTO.SudokuDTO;
import com.sudoku.api.Resolvers.BacktrackingResolver;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sudoku-api/backtracking")
@CrossOrigin
@Log4j2
public class BacktrackingController {

    /**
     * Returns a puzzle created by backtracking resolver.
     * @param numberOfFields number of fields that are hidden.
     * @return
     */
    @GetMapping("/getPuzzle")
    public ResponseEntity<Object> getBoardFromResolverFactory(@RequestParam("numberOfFields") Integer numberOfFields) {
        SudokuResolverFactory sf = new SudokuResolverFactory();
        sf.setResolver(new BacktrackingResolver());
        sf.setNumberOfHiddenFields(numberOfFields);
        SudokuDAO sudoku = sf.create();
        return new ResponseEntity<>(SudokuDTO.fromSudokuDAO(sudoku), HttpStatus.OK);
    }

    @PostMapping("/getMoves")
    public ResponseEntity<Object> getMovesFromResolver(@RequestBody SudokuDTO sudoku) {
        log.info(sudoku);
        BacktrackingResolver resolver = new BacktrackingResolver();
        List<ResolverMoveDTO> moves = resolver.getMoves(SudokuDAO.fromDTO(sudoku));

        return new ResponseEntity<>(moves, HttpStatus.OK);
    }
}
