package org.rolintensificado.rolcompanion.config;

import org.rolintensificado.rolcompanion.model.Campaign;
import org.rolintensificado.rolcompanion.model.CampaignStatus;
import org.rolintensificado.rolcompanion.model.Game;
import org.rolintensificado.rolcompanion.model.Player;
import org.rolintensificado.rolcompanion.repository.CampaignRepository;
import org.rolintensificado.rolcompanion.repository.GameRepository;
import org.rolintensificado.rolcompanion.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initData(
            PlayerRepository playerRepository,
            GameRepository gameRepository,
            CampaignRepository campaignRepository
    ) {
        return args -> {
            if (playerRepository.count() == 0 && campaignRepository.count() == 0 && gameRepository.count() == 0) {

                Player dm = new Player();
                dm.setDisplayName("Mauro");
                dm.setRealName("Mauro el Master");
                playerRepository.save(dm);

                Game dnd = new Game();
                dnd.setName("Dungeons & Dragons 5e");
                dnd.setDescription("Juego clásico de fantasía épica con dados y dragones.");
                gameRepository.save(dnd);

                Campaign camp = new Campaign();
                camp.setName("Bodas & Bestias");
                camp.setSlug("bodas-bestias");
                camp.setStartDate(LocalDate.of(2024, 1, 1));
                camp.setLastSessionDate(LocalDate.now());
                camp.setShortDescription("Campaña DnD donde todos los enemigos quieren casarse con los jugadores");
                camp.setLongDescription("Una épica aventura donde el compromiso es literal.");
                camp.setStatus(CampaignStatus.ACTIVE);
                camp.setGameMaster(dm);
                camp.setSystem(dnd);
                campaignRepository.save(camp);
            }
        };
    }
}