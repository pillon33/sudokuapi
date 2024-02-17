package com.sudoku.api.Repositories;

import com.sudoku.api.Models.Entity.ResolverEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ResolverRepository extends CrudRepository<ResolverEntity, UUID> {
}
