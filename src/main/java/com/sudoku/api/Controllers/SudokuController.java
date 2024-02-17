package com.sudoku.api.Controllers;

import com.sudoku.api.Models.DTO.ResolverDTO;
import com.sudoku.api.Models.DTO.SudokuDTO;
import com.sudoku.api.Models.Entity.ResolverEntity;
import com.sudoku.api.Repositories.ResolverRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("sudoku-api")
@CrossOrigin
@Log4j2
public class SudokuController {
    @Autowired
    ResolverRepository resolverRepository;

    @GetMapping("/resolvers")
    public ResponseEntity<Object> getResolversList() {
        List<ResolverDTO> resolvers = new ArrayList<>();
        resolverRepository.findAll().forEach(entity -> {
            resolvers.add(ResolverDTO.fromMain(entity));
        });
        return new ResponseEntity<>(resolvers, HttpStatus.OK);
    }

    @PostMapping("/addResolvers")
    public ResponseEntity<Object> getResolversList(@RequestBody List<ResolverDTO> resolvers) {
        List<ResolverEntity> entities = resolvers
                .stream()
                .map(ResolverEntity::fromDTO)
                .toList();

        List<ResolverDTO> result = StreamSupport
                .stream(resolverRepository
                        .saveAll(entities)
                        .spliterator(),
                        false)
                .map(ResolverDTO::fromMain)
                .toList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
