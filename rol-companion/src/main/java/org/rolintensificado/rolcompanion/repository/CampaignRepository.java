package org.rolintensificado.rolcompanion.repository;

import org.rolintensificado.rolcompanion.model.Campaign;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CampaignRepository extends CrudRepository<Campaign, UUID> {

    Optional<Campaign> findBySlug(String slug);

    void deleteById(UUID id);

    boolean existsById(UUID id);

}
