package org.rolintensificado.rolcompanion.dto;

import jakarta.validation.constraints.NotBlank;

public class CampaignDTO {

    @NotBlank(message = "El nombre de la campaña es obligatorio")
    private String name;

    @NotBlank(message = "El slug es obligatorio")
    private String slug;

    @NotBlank(message = "La descripción corta es obligatoria")
    private String shortDescription;

    private String longDescription;

    @NotBlank(message = "El ID del juego es obligatorio")
    private String gameId;

    @NotBlank(message = "El ID del director es obligatorio")
    private String dmId;

    @NotBlank(message = "La fecha de inicio es obligatoria")
    private String startDate;

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getDmId() {
        return dmId;
    }

    public void setDmId(String dmId) {
        this.dmId = dmId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}