package org.rolintensificado.rolcompanion.service;

import org.rolintensificado.rolcompanion.model.FormField;
import org.rolintensificado.rolcompanion.repository.FormFieldRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FormFieldService {

    private final FormFieldRepository formFieldRepository;

    public FormFieldService(FormFieldRepository formFieldRepository) {
        this.formFieldRepository = formFieldRepository;
    }

    public Iterable<FormField> findAll() {
        return formFieldRepository.findAll();
    }

    public Optional<FormField> findById(UUID id) {
        return formFieldRepository.findById(id);
    }

    public FormField save(FormField field) {
        return formFieldRepository.save(field);
    }

    public Optional<FormField> update(UUID id, FormField updated) {
        return formFieldRepository.findById(id).map(existing -> {
            updated.setId(existing.getId());
            return formFieldRepository.save(updated);
        });
    }

    public boolean delete(UUID id) {
        if (formFieldRepository.existsById(id)) {
            formFieldRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
