package org.rolintensificado.rolcompanion.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String realName;
    private String displayName;

    @OneToMany(mappedBy = "player")
    private List<PlayerInCampaign> campaigns = new ArrayList<>();

    @OneToMany(mappedBy = "player")
    private List<Character> characters = new ArrayList<>();

    // Getters and Setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public List<PlayerInCampaign> getCampaigns() {
        return campaigns;
    }
    public void setCampaigns(List<PlayerInCampaign> campaigns) {
        this.campaigns = campaigns;
    }
    public List<Character> getCharacters() {
        return characters;
    }
    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
    public void addCampaign(PlayerInCampaign campaign) {
        this.campaigns.add(campaign);
        campaign.setPlayer(this);
    }
    public void removeCampaign(PlayerInCampaign campaign) {
        this.campaigns.remove(campaign);
        campaign.setPlayer(null);
    }
    public void addCharacter(Character character) {
        this.characters.add(character);
        character.setPlayer(this);
    }
}
