package com.sudoku.api.Resolvers;

import com.sudoku.api.Models.DAO.SudokuBlockDAO;
import com.sudoku.api.Models.DAO.SudokuCellDAO;
import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.DTO.ResolverMoveDTO;
import com.sudoku.api.Models.DTO.SudokuDTO;
import com.sudoku.api.Models.Other.BacktrackingData;
import com.sudoku.api.Services.SudokuService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Log4j2
public class OptimisedBacktrackingResolver implements Resolver {

    @Override
    public SudokuDAO resolve(SudokuDAO sudoku) {
        int row = 0;
        int col = 0;
        List<ResolverMoveDTO> moveHistory = new ArrayList<>();

        while (!sudoku.isSolved()) {
            SudokuService.removeIllegalCandidatesForBoard(sudoku);

            var coordinates = SudokuService.getCellCoordinatesWithLeastCandidates(sudoku);
            row = coordinates.get("row");
            col = coordinates.get("col");

            // all fields filled
            if (row == -1) {
                // backtracking
                BacktrackingData data = this.backtracking(sudoku, moveHistory);

                if (data.isErrorFlag()) {
                    throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
                }

                row = data.getRow();
                col = data.getCol();
            }

            var cell = sudoku.getCellAtPosition(row, col);

            if (cell.getCandidates().isEmpty()) {
                // backtracking
                BacktrackingData data = this.backtracking(sudoku, moveHistory);

                if (data.isErrorFlag()) {
                    throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
                }

                continue;
            }

            var value = cell.getCandidates().get(0);

            cell.setValue(value);
            cell.setCheckedCandidates(new ArrayList<>(cell.getCandidates()));
            moveHistory.addFirst(new ResolverMoveDTO(row, col, value, cell.getCandidates()));
        }

        return sudoku;
    }

    @Override
    public Boolean isPuzzle(SudokuDAO sudoku) {
        int row = 0;
        int col = 0;
        List<ResolverMoveDTO> moveHistory = new ArrayList<>();
        int numberOfSolutions = 0;

        while (!sudoku.isSolved()) {
            SudokuService.removeIllegalCandidatesForBoard(sudoku);

            var coordinates = SudokuService.getCellCoordinatesWithLeastCandidates(sudoku);
            row = coordinates.get("row");
            col = coordinates.get("col");

            // all fields filled
            if (row == -1) {
                // backtracking
                BacktrackingData data = this.backtracking(sudoku, moveHistory);

                if (data.isErrorFlag()) {
                    return numberOfSolutions == 1;
                }

                row = data.getRow();
                col = data.getCol();
            }

            var cell = sudoku.getCellAtPosition(row, col);

            if (cell.getCandidates().isEmpty()) {
                // backtracking
                BacktrackingData data = this.backtracking(sudoku, moveHistory);

                if (data.isErrorFlag()) {
                    return numberOfSolutions == 1;
                }

                continue;
            }

            var value = cell.getCandidates().get(0);

            cell.setValue(value);
            cell.setCheckedCandidates(new ArrayList<>(cell.getCandidates()));
            moveHistory.addFirst(new ResolverMoveDTO(row, col, value, cell.getCandidates()));

            if (sudoku.isSolved()) {
                numberOfSolutions++;

                if (numberOfSolutions > 1) {
                    return false;
                }

                BacktrackingData data = this.backtracking(sudoku, moveHistory);

                if (data.isErrorFlag()) {
                    return true;
                }
            }
        }

        return numberOfSolutions == 1;
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

        while (!sudoku.isSolved()) {
            SudokuService.removeIllegalCandidatesForBoard(sudoku);

            var coordinates = SudokuService.getCellCoordinatesWithLeastCandidates(sudoku);
            row = coordinates.get("row");
            col = coordinates.get("col");

            // all fields filled
            if (row == -1) {
                // backtracking
                BacktrackingData data = this.backtracking(sudoku, moveHistory, history);

                if (data.isErrorFlag()) {
                    return history;
                }

                row = data.getRow();
                col = data.getCol();
            }

            var cell = sudoku.getCellAtPosition(row, col);

            if (cell.getCandidates().isEmpty()) {
                // backtracking
                BacktrackingData data = this.backtracking(sudoku, moveHistory, history);

                if (data.isErrorFlag()) {
                    return history;
                }

                continue;
            }

            var value = cell.getCandidates().get(0);

            cell.setValue(value);
            cell.setCheckedCandidates(new ArrayList<>(cell.getCandidates()));
            var move = new ResolverMoveDTO(row, col, value, cell.getCandidates());
            moveHistory.addFirst(move);
            history.add(move);
        }

        return history;
    }

    private BacktrackingData backtracking(SudokuDAO sudoku, List<ResolverMoveDTO> moveHistory) {
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
            cell.setCandidates(new ArrayList<>(cell.getCheckedCandidates()));

            cell.removeCandidate(move.getInsertedValue());
            cell.setValue(0);

            if (cell.getCandidates().size() == 0) {
                // keep backtracking
                continue;
            }

            cell.setValue(1);
            this.resetCandidatesForEmptyCells(sudoku);
            cell.setValue(0);


            // finished backtracking
            break;
        }

        result.setCol(col);
        result.setRow(row);
        result.setSudoku(sudoku);
        result.setMoveHistory(moveHistory);

        if (moveHistory.isEmpty() && cell.getCandidates().isEmpty()) {
            // searched all possible values and couldn't find solution
            result.setErrorFlag(true);
        }

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
            cell.setCandidates(new ArrayList<>(cell.getCheckedCandidates()));

            cell.removeCandidate(move.getInsertedValue());
            cell.setValue(0);

            var moveToAdd = new ResolverMoveDTO(row, col, cell.getValue(), cell.getCandidates());


            if (cell.getCandidates().size() == 0) {
                // keep backtracking
                history.add(moveToAdd);
                continue;
            }

            cell.setValue(1);
            this.resetCandidatesForEmptyCells(sudoku);
            cell.setValue(0);


            // finished backtracking
            break;
        }

        result.setCol(col);
        result.setRow(row);
        result.setSudoku(sudoku);
        result.setMoveHistory(moveHistory);

        if (moveHistory.isEmpty() && cell.getCandidates().isEmpty()) {
            // searched all possible values and couldn't find solution
            result.setErrorFlag(true);
        }

        return result;
    }

    private void resetCandidatesForEmptyCells(SudokuDAO sudoku) {
        sudoku.getBlocks().forEach((block) -> block.getCells().forEach((cell) -> {
            if (cell.getValue() == 0) {
                cell.setDefaultCandidates();
            }
//            cell.setDefaultCandidates();
        }));
    }
}
