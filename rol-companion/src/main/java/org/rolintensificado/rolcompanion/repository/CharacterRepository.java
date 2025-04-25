package org.rolintensificado.rolcompanion.repository;

import org.springframework.data.repository.CrudRepository;
import org.rolintensificado.rolcompanion.model.Character;

import java.util.UUID;

public interface CharacterRepository extends CrudRepository<Character, UUID> {
}
