package com.sudoku.api.Models.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DifficultyData {
    Long numberOfMoves;
    Long numberOfMistakes;
}
