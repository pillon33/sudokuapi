package com.sudoku.api.Models.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResolverMove {
    private final Integer row;
    private final Integer column;
    private final Integer insertedValue;
    private final List<Integer> candidates;

    public ResolverMove(Integer row, Integer column, Integer insertedValue, List<Integer> candidates) {
        this.row = row;
        this.column = column;
        this.insertedValue = insertedValue;
        this.candidates = candidates;
    }
}
