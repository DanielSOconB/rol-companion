package org.rolintensificado.rolcompanion.service;

import org.rolintensificado.rolcompanion.dto.CampaignDTO;
import org.rolintensificado.rolcompanion.model.Campaign;
import org.rolintensificado.rolcompanion.model.CampaignStatus;
import org.rolintensificado.rolcompanion.model.Game;
import org.rolintensificado.rolcompanion.model.Player;
import org.rolintensificado.rolcompanion.repository.CampaignRepository;
import org.rolintensificado.rolcompanion.repository.GameRepository;
import org.rolintensificado.rolcompanion.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
            throw new IllegalArgumentException("El sistema es obligatorio.");
        }
        if (dto.getDmId() == null) {
            throw new IllegalArgumentException("El director es obligatorio.");
        }
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
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

    public void deleteCampaign(UUID id) {
        campaignRepository.deleteById(id);
    }
}