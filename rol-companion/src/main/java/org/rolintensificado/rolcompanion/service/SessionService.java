package org.rolintensificado.rolcompanion.service;

import org.rolintensificado.rolcompanion.model.Session;
import org.rolintensificado.rolcompanion.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Iterable<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Optional<Session> findById(UUID id) {
        return sessionRepository.findById(id);
    }

    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    public Optional<Session> update(UUID id, Session updated) {
        return sessionRepository.findById(id).map(existing -> {
            updated.setId(existing.getId());
            return sessionRepository.save(updated);
        });
    }

    public boolean delete(UUID id) {
        if (sessionRepository.existsById(id)) {
            sessionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
