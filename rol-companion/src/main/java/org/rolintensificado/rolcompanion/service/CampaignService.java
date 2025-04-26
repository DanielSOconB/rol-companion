package org.rolintensificado.rolcompanion.service;

import org.rolintensificado.rolcompanion.dto.CampaignDTO;
import org.rolintensificado.rolcompanion.model.Campaign;
import org.rolintensificado.rolcompanion.model.CampaignStatus;
import org.rolintensificado.rolcompanion.model.Game;
import org.rolintensificado.rolcompanion.model.Player;
import org.rolintensificado.rolcompanion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PlayerInCampaignRepository playerInCampaignRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    public Campaign createCampaign(CampaignDTO dto) {
        campaignRepository.findBySlug(dto.getSlug())
            .ifPresent(existing -> {
                throw new IllegalArgumentException("A campaign with the slug '" + dto.getSlug() + "' already exists.");
            });

        Game game = gameRepository.findById(UUID.fromString(dto.getGameId()))
                .orElseThrow(() -> new IllegalArgumentException("Juego no encontrado"));

        Player director = playerRepository.findById(UUID.fromString(dto.getDmId()))
                .orElseThrow(() -> new IllegalArgumentException("Director no encontrado"));

        Campaign campaign = new Campaign();
        campaign.setName(dto.getName());
        campaign.setSlug(dto.getSlug());
        campaign.setShortDescription(dto.getShortDescription());
        campaign.setLongDescription(dto.getLongDescription());
        campaign.setSystem(game);
        campaign.setGameMaster(director);
        campaign.setStatus(CampaignStatus.PLANNED);
        campaign.setStartDate(dto.getStartDate() != null ? LocalDate.parse(dto.getStartDate()) : LocalDate.now());

        if (dto.getGameId() == null) {
            throw new IllegalArgumentException("System is required.");
        }
        if (dto.getDmId() == null) {
            throw new IllegalArgumentException("Game Master is required.");
        }
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required.");
        }


        UUID directorId = UUID.fromString(dto.getDmId());
        Player gameMaster = playerService.findById(directorId)
            .orElseThrow(() -> new IllegalArgumentException("Player not found with ID: " + directorId));
        campaign.setGameMaster(gameMaster);

        UUID gameId = UUID.fromString(dto.getGameId());
        Game system = gameService.findById(gameId)
            .orElseThrow(() -> new IllegalArgumentException("Game not found with ID: " + gameId));
        campaign.setSystem(system);

        return campaignRepository.save(campaign);
    }

    public boolean slugExists(String slug) {
        return campaignRepository.findBySlug(slug).isPresent();
    }

    public List<Campaign> getAllCampaigns() {
        return (List<Campaign>) campaignRepository.findAll();
    }

    public Optional<Campaign> getCampaignById(UUID id) {
        return campaignRepository.findById(id);
    }

    public Optional<Campaign> findCampaignBySlug(String slug) {
        return campaignRepository.findBySlug(slug);
    }

    @Transactional
    public void deleteCampaign(UUID campaignId) {
        // Log para verificar el ID recibido
        System.out.println("Intentando eliminar campaña con ID: " + campaignId);

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> {
                    System.out.println("Campaña no encontrada con ID: " + campaignId);
                    return new NoSuchElementException("La campaña no existe");
                });

        // Log para confirmar que la campaña fue encontrada
        System.out.println("Campaña encontrada: " + campaign.getName());

        sessionRepository.deleteAllByCampaign(campaign);
        campaignRepository.deleteById(campaignId);
    }

    @Transactional
    public void updateCampaignStatus(UUID id, String newStatus) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Campaign " + id + " not found"));

        try {
            CampaignStatus status = CampaignStatus.valueOf(newStatus.toUpperCase());
            campaign.setStatus(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid State: " + newStatus);
        }

        campaignRepository.save(campaign);
    }

}