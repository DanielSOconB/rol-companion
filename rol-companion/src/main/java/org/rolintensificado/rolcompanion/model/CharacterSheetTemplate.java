package org.rolintensificado.rolcompanion.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class CharacterSheetTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToOne
    private Game game;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
    private List<FormField> fields = new ArrayList<>();

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
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public List<FormField> getFields() {
        return fields;
    }
    public void setFields(List<FormField> fields) {
        this.fields = fields;
    }
    public void addField(FormField field) {
        fields.add(field);
        field.setTemplate(this);
    }
    public void removeField(FormField field) {
        fields.remove(field);
        field.setTemplate(null);
    }
    public void clearFields() {
        for (FormField field : fields) {
            field.setTemplate(null);
        }
        fields.clear();
    }
    public void setFieldsAndTemplate(List<FormField> fields) {
        clearFields();
        for (FormField field : fields) {
            addField(field);
        }
    }
    @Override
    public String toString() {
        return "CharacterSheetTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", game=" + game +
                ", fields=" + fields +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterSheetTemplate)) return false;

        CharacterSheetTemplate that = (CharacterSheetTemplate) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        return game.equals(that.game);
    }
}
