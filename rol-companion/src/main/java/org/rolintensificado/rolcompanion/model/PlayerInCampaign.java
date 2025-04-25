package org.rolintensificado.rolcompanion.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class PlayerInCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Campaign campaign;

    @Enumerated(EnumType.STRING)
    private CampaignRole role;

    @OneToMany(mappedBy = "playerInCampaign")
    private List<Character> characters = new ArrayList<>();

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public CampaignRole getRole() {
        return role;
    }

    public void setRole(CampaignRole role) {
        this.role = role;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
