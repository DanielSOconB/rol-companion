package org.rolintensificado.rolcompanion.service;

import org.rolintensificado.rolcompanion.model.Game;
import org.rolintensificado.rolcompanion.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Iterable<Game> findAll() {
        return gameRepository.findAll();
    }

    public Optional<Game> findById(UUID id) {
        return gameRepository.findById(id);
    }

    public Game save(Game game) {
        return gameRepository.save(game);
    }

    public Optional<Game> update(UUID id, Game updated) {
        return gameRepository.findById(id).map(existing -> {
            updated.setId(existing.getId());
            return gameRepository.save(updated);
        });
    }

    public boolean delete(UUID id) {
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
            return true;
        }
        return false;
    }
}