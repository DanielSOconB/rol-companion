package org.rolintensificado.rolcompanion.service;

import org.rolintensificado.rolcompanion.model.CharacterSheetTemplate;
import org.rolintensificado.rolcompanion.repository.CharacterSheetTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CharacterSheetTemplateService {

    private final CharacterSheetTemplateRepository repository;

    public CharacterSheetTemplateService(CharacterSheetTemplateRepository repository) {
        this.repository = repository;
    }

    public Iterable<CharacterSheetTemplate> findAll() {
        return repository.findAll();
    }

    public Optional<CharacterSheetTemplate> findById(UUID id) {
        return repository.findById(id);
    }

    public CharacterSheetTemplate save(CharacterSheetTemplate template) {
        return repository.save(template);
    }

    public Optional<CharacterSheetTemplate> update(UUID id, CharacterSheetTemplate updated) {
        return repository.findById(id).map(existing -> {
            updated.setId(existing.getId());
            return repository.save(updated);
        });
    }

    public boolean delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
