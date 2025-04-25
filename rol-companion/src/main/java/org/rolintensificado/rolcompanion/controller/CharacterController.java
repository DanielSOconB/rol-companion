package org.rolintensificado.rolcompanion.controller;

import org.rolintensificado.rolcompanion.model.Character;
import org.rolintensificado.rolcompanion.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public Iterable<Character> getAll() {
        return characterService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getById(@PathVariable UUID id) {
        Optional<Character> character = characterService.findById(id);
        return character.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Character create(@RequestBody Character character) {
        return characterService.save(character);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Character> update(@PathVariable UUID id, @RequestBody Character updated) {
        return characterService.update(id, updated)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (characterService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

