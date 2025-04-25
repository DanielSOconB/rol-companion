package org.rolintensificado.rolcompanion.controller;

import org.rolintensificado.rolcompanion.model.CharacterSheetTemplate;
import org.rolintensificado.rolcompanion.service.CharacterSheetTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/templates")
public class CharacterSheetTemplateController {

    private final CharacterSheetTemplateService templateService;

    public CharacterSheetTemplateController(CharacterSheetTemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public Iterable<CharacterSheetTemplate> getAll() {
        return templateService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterSheetTemplate> getById(@PathVariable UUID id) {
        Optional<CharacterSheetTemplate> template = templateService.findById(id);
        return template.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CharacterSheetTemplate create(@RequestBody CharacterSheetTemplate template) {
        return templateService.save(template);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterSheetTemplate> update(@PathVariable UUID id, @RequestBody CharacterSheetTemplate updated) {
        return templateService.update(id, updated)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (templateService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}