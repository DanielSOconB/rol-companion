package org.rolintensificado.rolcompanion.controller;

import org.rolintensificado.rolcompanion.model.Session;
import org.rolintensificado.rolcompanion.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public Iterable<Session> getAll() {
        return sessionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Session> getById(@PathVariable UUID id) {
        Optional<Session> session = sessionService.findById(id);
        return session.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Session create(@RequestBody Session session) {
        return sessionService.save(session);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Session> update(@PathVariable UUID id, @RequestBody Session updated) {
        return sessionService.update(id, updated)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (sessionService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}