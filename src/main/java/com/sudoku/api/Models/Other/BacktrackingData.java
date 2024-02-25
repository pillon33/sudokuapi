package com.sudoku.api.Models.Other;

import com.sudoku.api.Models.DAO.SudokuDAO;
import com.sudoku.api.Models.DTO.ResolverMoveDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BacktrackingData {
    private SudokuDAO sudoku;
    private List<ResolverMoveDTO> moveHistory;
    private int row;
    private int col;
    private boolean errorFlag;
}
