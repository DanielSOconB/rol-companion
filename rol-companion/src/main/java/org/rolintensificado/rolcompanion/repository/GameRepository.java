package org.rolintensificado.rolcompanion.repository;

import org.rolintensificado.rolcompanion.model.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GameRepository extends CrudRepository<Game, UUID> {
}

