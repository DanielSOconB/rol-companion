package org.rolintensificado.rolcompanion.controller;

import org.rolintensificado.rolcompanion.model.FormField;
import org.rolintensificado.rolcompanion.service.FormFieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/fields")
public class FormFieldController {

    private final FormFieldService formFieldService;

    public FormFieldController(FormFieldService formFieldService) {
        this.formFieldService = formFieldService;
    }

    @GetMapping
    public Iterable<FormField> getAll() {
        return formFieldService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormField> getById(@PathVariable UUID id) {
        Optional<FormField> field = formFieldService.findById(id);
        return field.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public FormField create(@RequestBody FormField field) {
        return formFieldService.save(field);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormField> update(@PathVariable UUID id, @RequestBody FormField updated) {
        return formFieldService.update(id, updated)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (formFieldService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}