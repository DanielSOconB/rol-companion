package org.rolintensificado.rolcompanion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public class GameDTO {
    private UUID id;

    @NotBlank(message = "Game name is required")
    @Size(min = 3, max = 100, message = "Game name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "System is required")
    @Size(min = 2, max = 100, message = "System name must be between 2 and 100 characters")
    private String system;

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

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
}