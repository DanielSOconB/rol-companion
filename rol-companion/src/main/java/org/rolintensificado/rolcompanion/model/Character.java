package org.rolintensificado.rolcompanion.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CharacterStatus status;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Campaign campaign;

    @ManyToOne
    private CharacterSheetTemplate template;

    @Lob
    private String attributes;

    @ManyToOne
    private PlayerInCampaign playerInCampaign;

    // Getters and Setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public CharacterStatus getStatus() {
        return status;
    }
    public void setStatus(CharacterStatus status) {
        this.status = status;
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
    public CharacterSheetTemplate getTemplate() {
        return template;
    }
    public void setTemplate(CharacterSheetTemplate template) {
        this.template = template;
    }
    public String getAttributes() {
        return attributes;
    }
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
    public PlayerInCampaign getPlayerInCampaign() {
        return playerInCampaign;
    }
    public void setPlayerInCampaign(PlayerInCampaign playerInCampaign) {
        this.playerInCampaign = playerInCampaign;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", player=" + player +
                ", campaign=" + campaign +
                ", template=" + template +
                ", attributes='" + attributes + '\'' +
                ", playerInCampaign=" + playerInCampaign +
                '}';
    }
    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character character)) return false;

        if (!getId().equals(character.getId())) return false;
        if (!getName().equals(character.getName())) return false;
        if (getStatus() != character.getStatus()) return false;
        if (!getPlayer().equals(character.getPlayer())) return false;
        if (!getCampaign().equals(character.getCampaign())) return false;
        if (!getTemplate().equals(character.getTemplate())) return false;
        if (!getAttributes().equals(character.getAttributes())) return false;
        return getPlayerInCampaign().equals(character.getPlayerInCampaign());
    }
}
