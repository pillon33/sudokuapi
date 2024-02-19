package com.sudoku.api.Factories.Concrete;

import com.sudoku.api.Factories.Abstract.SudokuFactory;
import com.sudoku.api.Models.DAO.SudokuCellDAO;
import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.Enums.BaseSudokuType;
import com.sudoku.api.Resolvers.Resolver;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Random;

@Log4j2
@Getter
@Setter
public class SudokuResolverFactory implements SudokuFactory {
    private Resolver resolver;
    private int numberOfHiddenFields = 5;
    private int loopIterationLimit = 100*40;
    private Random random = new Random();

    @Override
    public SudokuDAO createBoard() {
        BaseSudokuFactory bsf = new BaseSudokuFactory();

        bsf.setType(BaseSudokuType.RANDOM_DIAGONAL);

        SudokuDAO test = bsf.createBoard();
        log.info(String.format("\n%s", test.isSolved()));

        SudokuDAO sudoku = resolver.resolve(test);

        return sudoku.getResolvedBoard();
    }

    @Override
    public SudokuDAO createPuzzle(SudokuDAO sudoku) {
        log.info("Starting with board:");
        log.info(String.format("\n %s", sudoku));
        SudokuDAO inputBoard = new SudokuDAO(sudoku);
        int hiddenFields = 0;
        int iterations = 0;
        int col = 0;
        int row = 0;

        while (hiddenFields < numberOfHiddenFields) {
            col = random.nextInt(0, 9);
            row = random.nextInt(0, 9);

            SudokuCellDAO cell = sudoku.getCellAtPosition(row, col);

            cell.setIsClue(false);
            cell.setValue(0);
            cell.setDefaultCandidates();

            sudoku.setCellAtPosition(row, col, cell);

            if (resolver.isPuzzle(sudoku)) {
                hiddenFields++;
            } else {
                cell.setIsClue(true);
                cell.setValue(cell.getSolution());
                sudoku.setCellAtPosition(row, col, cell);
            }

            if (iterations >= this.loopIterationLimit) {
                hiddenFields = 0;
                sudoku = new SudokuDAO(inputBoard);
            }
        }
        return sudoku;
    }
}
