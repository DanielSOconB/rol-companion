package org.rolintensificado.rolcompanion.repository;

import org.rolintensificado.rolcompanion.model.Campaign;
import org.rolintensificado.rolcompanion.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    void deleteAllByCampaign(Campaign campaign);

    void deleteByCampaignId(UUID id);
}
