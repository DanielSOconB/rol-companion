package org.rolintensificado.rolcompanion.repository;

import org.rolintensificado.rolcompanion.model.FormField;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FormFieldRepository extends CrudRepository<FormField, UUID> {
}

