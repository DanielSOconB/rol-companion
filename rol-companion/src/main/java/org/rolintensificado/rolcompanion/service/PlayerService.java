package org.rolintensificado.rolcompanion.service;

import org.rolintensificado.rolcompanion.model.Player;
import org.rolintensificado.rolcompanion.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Iterable<Player> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findById(UUID id) {
        return playerRepository.findById(id);
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public Optional<Player> update(UUID id, Player updated) {
        return playerRepository.findById(id).map(existing -> {
            updated.setId(existing.getId());
            return playerRepository.save(updated);
        });
    }

    public boolean delete(UUID id) {
        if (playerRepository.existsById(id)) {
            playerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

