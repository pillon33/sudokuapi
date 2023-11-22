package com.sudoku.api.Resolvers;

import com.sudoku.api.Models.DAO.SudokuCellDAO;
import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.DTO.ResolverMove;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log4j2
public class BacktrackingResolver implements Resolver {
    @Override
    public SudokuDAO resolve(SudokuDAO sudoku) {
        int row = 0;
        int col = 0;
        int firstEmptyCellRow = -1;
        int firstEmptyCellCol = -1;
        int lastEditedRow = 0;
        int lastEditedCol = 0;

        while (!sudoku.isSolved()) {
            // went through all fields and couldn't find any solution
            if (row >= 9) {
                row = 8;
                col = 8;
                // tried all values for this cell, step back
                while (sudoku.getCellAtPosition(row, col).getIsClue()) {
                    // tried all possible values and couldn't find any solution
                    if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                        return null;
                    }

                    row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                    col = col == 0 ? 8 : col - 1;
                }
            }

            SudokuCellDAO cell = sudoku.getCellAtPosition(row, col);
            if (!cell.getIsClue()) {
                if (firstEmptyCellRow == -1 && firstEmptyCellCol == -1) {
                    firstEmptyCellRow = row;
                    firstEmptyCellCol = col;
                }

                cell.removeCandidate(cell.getValue());

                // check if there is still any candidate for cell
                if (cell.getCandidates().size() == 0) {
                    cell.setDefaultCandidates();
                    cell.setValue(0);
                    sudoku.setCellAtPosition(row, col, cell);

                    // tried all possible values and couldn't find any solution
                    if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                        return null;
                    }

                    // tried all values for this cell, step back
                    row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                    col = col == 0 ? 8 : col - 1;

                    // step back to first non clue field
                    while (sudoku.getCellAtPosition(row, col).getIsClue()) {
                        // tried all possible values and couldn't find any solution
                        if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                            return null;
                        }

                        row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                        col = col == 0 ? 8 : col - 1;
                    }

                    continue;
                }

                // try next candidate for this cell
                cell.setValue(cell.getCandidates().get(0));
                sudoku.setCellAtPosition(row, col, cell);

                // find candidate that doesn't cause mistake
                while (!sudoku.isCorrectOptimisedForLastMove(row, col)) {
                    cell.removeCandidate(cell.getValue());

                    if (cell.getCandidates().size() == 0) {
                        break;
                    }

                    // try next candidate for this cell
                    cell.setValue(cell.getCandidates().get(0));
                    sudoku.setCellAtPosition(row, col, cell);
                }

                // check if there is still any candidate for cell
                if (cell.getCandidates().size() == 0) {
                    cell.setDefaultCandidates();
                    cell.setValue(0);
                    sudoku.setCellAtPosition(row, col, cell);

                    // tried all possible values and couldn't find any solution
                    if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                        return null;
                    }

                    // tried all values for this cell, step back
                    row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                    col = col == 0 ? 8 : col - 1;

                    // step back to first non clue field
                    while (sudoku.getCellAtPosition(row, col).getIsClue()) {
                        // tried all possible values and couldn't find any solution
                        if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                            return null;
                        }

                        row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                        col = col == 0 ? 8 : col - 1;
                    }

                    continue;
                }
            }

            // go to next cell
            col = (col + 1) % 9;
            row = col == 0 ? (row + 1) : row;
        }

        return sudoku;
    }

    @Override
    public Boolean isPuzzle(SudokuDAO sudoku) {
        int row = 0;
        int col = 0;
        int firstEmptyCellRow = -1;
        int firstEmptyCellCol = -1;
        int lastEditedRow = 0;
        int lastEditedCol = 0;
        int numberOfSolutions = 0;

        while (!sudoku.isSolved()) {
            // went through all fields and couldn't find any solution
            if (row >= 9) {
                row = 8;
                col = 8;
                // tried all values for this cell, step back
                while (sudoku.getCellAtPosition(row, col).getIsClue()) {
                    // tried all possible values and couldn't find any solution
                    if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                        return numberOfSolutions == 1;
                    }

                    row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                    col = col == 0 ? 8 : col - 1;
                }
            }

            SudokuCellDAO cell = sudoku.getCellAtPosition(row, col);
            if (!cell.getIsClue()) {
                if (firstEmptyCellRow == -1 && firstEmptyCellCol == -1) {
                    firstEmptyCellRow = row;
                    firstEmptyCellCol = col;
                }

                cell.removeCandidate(cell.getValue());

                // check if there is still any candidate for cell
                if (cell.getCandidates().size() == 0) {
                    cell.setDefaultCandidates();
                    cell.setValue(0);
                    sudoku.setCellAtPosition(row, col, cell);

                    // tried all possible values and couldn't find any solution
                    if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                        return numberOfSolutions == 1;
                    }

                    // tried all values for this cell, step back
                    row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                    col = col == 0 ? 8 : col - 1;

                    // step back to first non clue field
                    while (sudoku.getCellAtPosition(row, col).getIsClue()) {
                        // tried all possible values and couldn't find any solution
                        if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                            return numberOfSolutions == 1;
                        }

                        row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                        col = col == 0 ? 8 : col - 1;
                    }

                    continue;
                }

                // try next candidate for this cell
                cell.setValue(cell.getCandidates().get(0));
                sudoku.setCellAtPosition(row, col, cell);

                // find candidate that doesn't cause mistake
                while (!sudoku.isCorrectOptimisedForLastMove(row, col)) {
                    cell.removeCandidate(cell.getValue());

                    if (cell.getCandidates().size() == 0) {
                        break;
                    }

                    // try next candidate for this cell
                    cell.setValue(cell.getCandidates().get(0));
                    sudoku.setCellAtPosition(row, col, cell);
                }

                // check if there is still any candidate for cell
                if (cell.getCandidates().size() == 0) {
                    cell.setDefaultCandidates();
                    cell.setValue(0);
                    sudoku.setCellAtPosition(row, col, cell);

                    // tried all possible values and couldn't find any solution
                    if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                        return numberOfSolutions == 1;
                    }

                    // tried all values for this cell, step back
                    row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                    col = col == 0 ? 8 : col - 1;

                    // step back to first non clue field
                    while (sudoku.getCellAtPosition(row, col).getIsClue()) {
                        // tried all possible values and couldn't find any solution
                        if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                            return numberOfSolutions == 1;
                        }

                        row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                        col = col == 0 ? 8 : col - 1;
                    }

                    continue;
                }
            }

            if (sudoku.isSolved()) {
                numberOfSolutions++;

                if (numberOfSolutions > 1) {
                    return false;
                }

                cell.setDefaultCandidates();
                cell.setValue(0);
                sudoku.setCellAtPosition(row, col, cell);

                // tried all possible values and couldn't find any solution
                if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                    return true;
                }

                // tried all values for this cell, step back
                row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                col = col == 0 ? 8 : col - 1;

                // step back to first non clue field
                while (sudoku.getCellAtPosition(row, col).getIsClue()) {
                    // tried all possible values and couldn't find any solution
                    if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                        return true;
                    }

                    row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                    col = col == 0 ? 8 : col - 1;
                }

                continue;
            }

            // go to next cell
            col = (col + 1) % 9;
            row = col == 0 ? (row + 1) : row;
        }

        return numberOfSolutions == 1;
    }

    @Override
    public int countSolutions(SudokuDAO sudoku) {
        return 0;
    }

    @Override
    public List<ResolverMove> getMoves(SudokuDAO sudoku) {
        int row = 0;
        int col = 0;
        int firstEmptyCellRow = -1;
        int firstEmptyCellCol = -1;
        int lastEditedRow = 0;
        int lastEditedCol = 0;
        List<ResolverMove> moves = new ArrayList<>();


        while (!sudoku.isSolved()) {
            // went through all fields and couldn't find any solution
            if (row >= 9) {
                row = 8;
                col = 8;
                // tried all values for this cell, step back
                while (sudoku.getCellAtPosition(row, col).getIsClue()) {
                    // tried all possible values and couldn't find any solution
                    if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                        return null;
                    }

                    row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                    col = col == 0 ? 8 : col - 1;
                }
            }

            SudokuCellDAO cell = sudoku.getCellAtPosition(row, col);
            if (!cell.getIsClue()) {
                if (firstEmptyCellRow == -1 && firstEmptyCellCol == -1) {
                    firstEmptyCellRow = row;
                    firstEmptyCellCol = col;
                }

                cell.removeCandidate(cell.getValue());

                // check if there is still any candidate for cell
                if (cell.getCandidates().size() == 0) {
                    cell.setDefaultCandidates();
                    cell.setValue(0);
                    sudoku.setCellAtPosition(row, col, cell);
                    ResolverMove move = new ResolverMove(row, col, cell.getValue(), cell.getCandidates());
                    moves.add(move);

                    // tried all possible values and couldn't find any solution
                    if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                        return null;
                    }

                    // tried all values for this cell, step back
                    row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                    col = col == 0 ? 8 : col - 1;

                    // step back to first non clue field
                    while (sudoku.getCellAtPosition(row, col).getIsClue()) {
                        // tried all possible values and couldn't find any solution
                        if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                            return null;
                        }

                        row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                        col = col == 0 ? 8 : col - 1;
                    }

                    continue;
                }

                // try next candidate for this cell
                cell.setValue(cell.getCandidates().get(0));
                sudoku.setCellAtPosition(row, col, cell);

                // find candidate that doesn't cause mistake
                while (!sudoku.isCorrectOptimisedForLastMove(row, col)) {
                    cell.removeCandidate(cell.getValue());

                    if (cell.getCandidates().size() == 0) {
                        break;
                    }

                    // try next candidate for this cell
                    cell.setValue(cell.getCandidates().get(0));
                    sudoku.setCellAtPosition(row, col, cell);
                }

                // check if there is still any candidate for cell
                if (cell.getCandidates().size() == 0) {
                    cell.setDefaultCandidates();
                    cell.setValue(0);
                    sudoku.setCellAtPosition(row, col, cell);
                    ResolverMove move = new ResolverMove(row, col, cell.getValue(), cell.getCandidates());
                    moves.add(move);

                    // tried all possible values and couldn't find any solution
                    if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                        return null;
                    }

                    // tried all values for this cell, step back
                    row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                    col = col == 0 ? 8 : col - 1;

                    // step back to first non clue field
                    while (sudoku.getCellAtPosition(row, col).getIsClue()) {
                        // tried all possible values and couldn't find any solution
                        if (row == firstEmptyCellRow && col == firstEmptyCellCol) {
                            return null;
                        }

                        row = col == 0 ? (row == 0 ? 8 : row - 1) : row;
                        col = col == 0 ? 8 : col - 1;
                    }

                    continue;
                }
                ResolverMove move = new ResolverMove(row, col, cell.getValue(), cell.getCandidates());
                moves.add(move);
            }

            // go to next cell
            col = (col + 1) % 9;
            row = col == 0 ? (row + 1) : row;
        }

        return moves;
    }
}
