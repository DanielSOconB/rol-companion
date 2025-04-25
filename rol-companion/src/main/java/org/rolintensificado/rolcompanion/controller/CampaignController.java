package org.rolintensificado.rolcompanion.controller;


import jakarta.validation.Valid;
import org.rolintensificado.rolcompanion.dto.CampaignDTO;
import org.rolintensificado.rolcompanion.model.Campaign;
import org.rolintensificado.rolcompanion.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "*")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @PostMapping
    public ResponseEntity<?> createCampaign(@Valid @RequestBody CampaignDTO dto) {
        System.out.println("Received DTO: " + dto);
        Campaign saved = campaignService.createCampaign(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.getAllCampaigns());
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable UUID id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build();
    }
}