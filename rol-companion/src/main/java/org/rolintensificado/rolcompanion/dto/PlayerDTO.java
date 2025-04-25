package org.rolintensificado.rolcompanion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public class PlayerDTO {
    private UUID id;

    @NotBlank(message = "Player name is required")
    @Size(min = 3, max = 100, message = "Player name must be between 3 and 100 characters")
    private String name;

    private boolean isDirector;

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

    public boolean isDirector() {
        return isDirector;
    }

    public void setDirector(boolean isDirector) {
        this.isDirector = isDirector;
    }
}
