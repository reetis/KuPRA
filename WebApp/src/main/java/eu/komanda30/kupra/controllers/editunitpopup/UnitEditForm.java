package eu.komanda30.kupra.controllers.editunitpopup;

import javax.validation.constraints.Size;

import org.apache.avro.reflect.Nullable;

public class UnitEditForm {
    @Nullable
    private Integer unitId;

    @Size(min=3, max=64)
    private String name;

    @Size(min=1, max=8)
    private String abbreviation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setUnitId(@Nullable Integer unitId) {
        this.unitId = unitId;
    }

    @Nullable
    public Integer getUnitId() {
        return unitId;
    }

    public boolean isEditForm() {
        return unitId != null;
    }
}
