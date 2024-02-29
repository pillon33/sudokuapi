package com.sudoku.api.Resolvers;

import com.sudoku.api.Models.DAO.SudokuCellDAO;
import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.DTO.ResolverMoveDTO;
import com.sudoku.api.Models.Other.BacktrackingData;
import com.sudoku.api.Services.SudokuService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBacktrackingResolver implements Resolver{
    @Override
    public SudokuDAO resolve(SudokuDAO sudoku) {
        int row = 0;
        int col = 0;
        List<ResolverMoveDTO> moveHistory = new ArrayList<>();
        Random random = new Random();
        int maxInsertedValues = 0;

        while (!sudoku.isSolved()) {
            // all fields filled
            if (sudoku.isFilled()) {
                // backtracking
                BacktrackingData data = this.backtracking(sudoku, moveHistory);

                if (data.isErrorFlag()) {
                    throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
                }

                row = data.getRow();
                col = data.getCol();
            }

            var cell = sudoku.getCellAtPosition(row, col);

            if (cell.getIsClue() | cell.getValue() != 0) {
                var emptyCells = SudokuService.getEmptyCellCoordinates(sudoku);
                var element = emptyCells.get(random.nextInt(0, emptyCells.size()));

                row = element.getRow();
                col = element.getCol();
                cell = sudoku.getCellAtPosition(row, col);
            }

            if (cell.getCandidates().isEmpty()) {
                // backtracking
                BacktrackingData data = this.backtracking(sudoku, moveHistory);

                if (moveHistory.size() > maxInsertedValues) {
                    maxInsertedValues = moveHistory.size();
                }

                if (data.isErrorFlag()) {
                    throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
                }

                row = data.getRow();
                col = data.getCol();

                continue;
            }

            cell.setValue(cell.getCandidates().get(0));
            cell.removeCandidate(cell.getValue());

            while (!sudoku.isCorrectOptimisedForLastMove(row, col) & !cell.getCandidates().isEmpty()) {
                cell.setValue(cell.getCandidates().get(0));
                cell.removeCandidate(cell.getValue());
            }

            moveHistory.addFirst(new ResolverMoveDTO(row, col, cell.getValue(), cell.getCandidates()));

            if (cell.getCandidates().isEmpty() & !sudoku.isCorrectOptimisedForLastMove(row, col)) {
                // backtracking
                BacktrackingData data = this.backtracking(sudoku, moveHistory);

                if (moveHistory.size() > maxInsertedValues) {
                    maxInsertedValues = moveHistory.size();
                }

                if (data.isErrorFlag()) {
                    throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
                }

                row = data.getRow();
                col = data.getCol();
            }
        }

        return sudoku;
    }

    @Override
    public Boolean isPuzzle(SudokuDAO sudoku) {
        return true;
    }

    @Override
    public int countSolutions(SudokuDAO sudoku) {
        return 0;
    }

    @Override
    public List<ResolverMoveDTO> getMoves(SudokuDAO sudoku) {
        int row = 0;
        int col = 0;
        List<ResolverMoveDTO> moveHistory = new ArrayList<>();
        List<ResolverMoveDTO> history = new ArrayList<>();
        Random random = new Random();
        int maxInsertedValues = 0;

        while (!sudoku.isSolved()) {
            // all fields filled
            if (sudoku.isFilled()) {
                // backtracking
                BacktrackingData data = this.backtracking(sudoku, moveHistory, history);

                if (data.isErrorFlag()) {
                    throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
                }

                row = data.getRow();
                col = data.getCol();
            }

            var cell = sudoku.getCellAtPosition(row, col);

            if (cell.getIsClue() | cell.getValue() != 0) {
                var emptyCells = SudokuService.getEmptyCellCoordinates(sudoku);
                var element = emptyCells.get(random.nextInt(0, emptyCells.size()));

                row = element.getRow();
                col = element.getCol();
                cell = sudoku.getCellAtPosition(row, col);
            }

            if (cell.getCandidates().isEmpty()) {
                // backtracking
                BacktrackingData data = this.backtracking(sudoku, moveHistory, history);

                if (moveHistory.size() > maxInsertedValues) {
                    maxInsertedValues = moveHistory.size();
                }

                if (data.isErrorFlag()) {
                    throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
                }

                row = data.getRow();
                col = data.getCol();

                continue;
            }

            cell.setValue(cell.getCandidates().get(0));
            cell.removeCandidate(cell.getValue());

            while (!sudoku.isCorrectOptimisedForLastMove(row, col) & !cell.getCandidates().isEmpty()) {
                cell.setValue(cell.getCandidates().get(0));
                cell.removeCandidate(cell.getValue());
            }

            var m = new ResolverMoveDTO(row, col, cell.getValue(), cell.getCandidates());
            moveHistory.addFirst(m);
            history.add(m);

            if (cell.getCandidates().isEmpty() & !sudoku.isCorrectOptimisedForLastMove(row, col)) {
                // backtracking
                BacktrackingData data = this.backtracking(sudoku, moveHistory, history);

                if (moveHistory.size() > maxInsertedValues) {
                    maxInsertedValues = moveHistory.size();
                }

                if (data.isErrorFlag()) {
                    throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
                }

                row = data.getRow();
                col = data.getCol();
            }

            if (history.size() > 1000) {
                return history;
            }
        }

        return history;
    }

    private BacktrackingData backtracking(SudokuDAO sudoku, List<ResolverMoveDTO> moveHistory) {
        BacktrackingData result = new BacktrackingData();
        int row = 0;
        int col = 0;
        SudokuCellDAO cell;

        if (moveHistory.isEmpty()) {
            result.setErrorFlag(true);
        }
        
        ResolverMoveDTO move;

        while (moveHistory.size() > 0) {
            move = moveHistory.getFirst();
            moveHistory.removeFirst();

            row = move.getRow();
            col = move.getColumn();

            cell = sudoku.getCellAtPosition(row, col);
            cell.setValue(0);

            if (cell.getCandidates().size() == 0) {
                if (moveHistory.isEmpty()) {
                    result.setErrorFlag(true);;
                }
                // keep backtracking
                cell.setDefaultCandidates();
                continue;
            }

            // finished backtracking
            break;
        }

        result.setCol(col);
        result.setRow(row);
        result.setSudoku(sudoku);
        result.setMoveHistory(moveHistory);

        return result;
    }

    private BacktrackingData backtracking(SudokuDAO sudoku, List<ResolverMoveDTO> moveHistory, List<ResolverMoveDTO> history) {
        BacktrackingData result = new BacktrackingData();
        int row = 0;
        int col = 0;
        SudokuCellDAO cell = new SudokuCellDAO(0, 0, false, new ArrayList<>(), new ArrayList<>());

        if (moveHistory.isEmpty()) {
            result.setErrorFlag(true);
        }

        while (moveHistory.size() > 0) {
            var move = moveHistory.getFirst();
            moveHistory.removeFirst();

            row = move.getRow();
            col = move.getColumn();

            cell = sudoku.getCellAtPosition(row, col);
            cell.setValue(0);

            var moveToAdd = new ResolverMoveDTO(row, col, cell.getValue(), cell.getCandidates());


            if (cell.getCandidates().size() == 0) {
                // keep backtracking
                if (moveHistory.isEmpty()) {
                    result.setErrorFlag(true);;
                }
                history.add(moveToAdd);
                cell.setDefaultCandidates();
                continue;
            }

            // finished backtracking
            break;
        }

        result.setCol(col);
        result.setRow(row);
        result.setSudoku(sudoku);
        result.setMoveHistory(moveHistory);

        return result;
    }
}
