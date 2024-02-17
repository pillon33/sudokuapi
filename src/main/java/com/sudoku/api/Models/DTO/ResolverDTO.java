package com.sudoku.api.Models.DTO;

import com.sudoku.api.Models.Entity.ResolverEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResolverDTO {
    private String name;
    private String menuTxt;
    private String menuDescription;
    private String redirectPath;

    public static ResolverDTO fromMain(ResolverEntity entity) {
        ResolverDTO dto = new ResolverDTO();

        dto.setName(entity.getName());
        dto.setMenuTxt(entity.getMenuTxt());
        dto.setMenuDescription(entity.getMenuDescription());
        dto.setRedirectPath(entity.getRedirectPath());

        return dto;
    }
}
