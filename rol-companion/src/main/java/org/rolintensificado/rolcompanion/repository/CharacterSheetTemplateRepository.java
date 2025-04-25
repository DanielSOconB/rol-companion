package org.rolintensificado.rolcompanion.repository;

import org.rolintensificado.rolcompanion.model.CharacterSheetTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CharacterSheetTemplateRepository extends CrudRepository<CharacterSheetTemplate, UUID> {
}

