package eu.komanda30.kupra.controllers.unit;

import javax.validation.constraints.Size;

public class NewUnitForm {
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
}
