package org.rolintensificado.rolcompanion.service;

import org.rolintensificado.rolcompanion.model.Character;
import org.rolintensificado.rolcompanion.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public Iterable<Character> findAll() {
        return characterRepository.findAll();
    }

    public Optional<Character> findById(UUID id) {
        return characterRepository.findById(id);
    }

    public Character save(Character character) {
        return characterRepository.save(character);
    }

    public Optional<Character> update(UUID id, Character updated) {
        return characterRepository.findById(id).map(existing -> {
            updated.setId(existing.getId());
            return characterRepository.save(updated);
        });
    }

    public boolean delete(UUID id) {
        if (characterRepository.existsById(id)) {
            characterRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
