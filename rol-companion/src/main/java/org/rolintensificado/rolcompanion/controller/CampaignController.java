package org.rolintensificado.rolcompanion.controller;


import jakarta.validation.Valid;
import org.rolintensificado.rolcompanion.dto.CampaignDTO;
import org.rolintensificado.rolcompanion.model.Campaign;
import org.rolintensificado.rolcompanion.service.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "*")
public class CampaignController {

    private static final Logger log = LoggerFactory.getLogger(CampaignController.class);
    @Autowired
    private CampaignService campaignService;

    @PostMapping
    public ResponseEntity<?> createCampaign(@Valid @RequestBody CampaignDTO dto) {
        Campaign saved = campaignService.createCampaign(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    @GetMapping("/slugs/{slug}")
    public ResponseEntity<Campaign> getCampaignBySlug(@PathVariable String slug) {
        Optional<Campaign> campaign = campaignService.findCampaignBySlug(slug);
        return campaign.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/validate-slug")
    public ResponseEntity<Map<String, Boolean>> validateSlug(@RequestParam String slug) {
        boolean exists = campaignService.slugExists(slug);
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable UUID id) {
        return campaignService.getCampaignById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateCampaignStatus(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        campaignService.updateCampaignStatus(id, newStatus);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable UUID id) {
        System.out.println("Recibido ID para eliminar: " + id);
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build();
    }

}