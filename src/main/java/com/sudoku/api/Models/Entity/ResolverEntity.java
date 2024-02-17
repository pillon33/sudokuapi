package com.sudoku.api.Models.Entity;

import com.sudoku.api.Models.DTO.ResolverDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resolvers", schema = "public")
public class ResolverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "name", columnDefinition = "varchar")
    private String name;
    @Column(name = "menutxt", columnDefinition = "varchar")
    private String menuTxt;
    @Column(name = "menudescription", columnDefinition = "varchar")
    private String menuDescription;
    @Column(name = "path", columnDefinition = "varchar")
    private String redirectPath;

    public static ResolverEntity fromDTO(ResolverDTO dto) {
        ResolverEntity entity = new ResolverEntity();

        entity.setName(dto.getName());
        entity.setMenuTxt(dto.getMenuTxt());
        entity.setMenuDescription(dto.getMenuDescription());
        entity.setRedirectPath(dto.getRedirectPath());

        return entity;
    }
}
