package org.rolintensificado.rolcompanion.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
public class FormField {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fieldName;

    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    @Lob
    private String options;

    @ManyToOne
    private CharacterSheetTemplate template;

    // Getters and Setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public FieldType getFieldType() {
        return fieldType;
    }
    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
    public String getOptions() {
        return options;
    }
    public void setOptions(String options) {
        this.options = options;
    }
    public CharacterSheetTemplate getTemplate() {
        return template;
    }
    public void setTemplate(CharacterSheetTemplate template) {
        this.template = template;
    }
    public void clearTemplate() {
        this.template = null;
    }
    public void setTemplateAndClearOld(CharacterSheetTemplate template) {
        if (this.template != null) {
            this.template.removeField(this);
        }
        this.template = template;
        if (template != null) {
            template.addField(this);
        }
    }

    @Override
    public String toString() {
        return "FormField{" +
                "id=" + id +
                ", fieldName='" + fieldName + '\'' +
                ", fieldType=" + fieldType +
                ", options='" + options + '\'' +
                ", template=" + (template != null ? template.getId() : null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormField formField)) return false;

        if (!id.equals(formField.id)) return false;
        if (!fieldName.equals(formField.fieldName)) return false;
        if (fieldType != formField.fieldType) return false;
        return Objects.equals(options, formField.options);
    }
}
