package org.rolintensificado.rolcompanion.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    private String slug;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate lastSessionDate;

    @Column(length = 200)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String longDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignStatus status;

    @ManyToOne(optional = false)
    private Player gameMaster;

    @OneToMany(mappedBy = "campaign", orphanRemoval = true)
    private List<Session> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "campaign")
    private List<PlayerInCampaign> players = new ArrayList<>();

    @ManyToOne(optional = false)
    private Game system;


    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }

    public void setSlug(String slug) { this.slug = slug; }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getLastSessionDate() { return lastSessionDate; }

    public void setLastSessionDate(LocalDate lastSessionDate) { this.lastSessionDate = lastSessionDate; }

    public String getShortDescription() { return shortDescription; }

    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getLongDescription() { return longDescription; }

    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }

    public CampaignStatus getStatus() { return status; }

    public void setStatus(CampaignStatus status) { this.status = status; }

    public Player getGameMaster() { return gameMaster; }

    public void setGameMaster(Player gameMaster) { this.gameMaster = gameMaster; }

    public List<Session> getSessions() { return sessions; }

    public void setSessions(List<Session> sessions) { this.sessions = sessions; }

    public List<PlayerInCampaign> getPlayers() { return players; }

    public void setPlayers(List<PlayerInCampaign> players) { this.players = players; }

    public Game getSystem() { return system; }

    public void setSystem(Game system) { this.system = system; }
}

