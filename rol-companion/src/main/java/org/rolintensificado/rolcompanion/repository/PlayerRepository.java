package org.rolintensificado.rolcompanion.repository;

import org.rolintensificado.rolcompanion.model.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PlayerRepository extends CrudRepository<Player, UUID> {
}