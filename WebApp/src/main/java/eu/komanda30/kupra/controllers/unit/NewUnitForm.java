package eu.komanda30.kupra.controllers.unit;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Rytis on 2014-10-21.
 */
public class NewUnitForm {
    @NotNull
    @Size(min=3, max=64)
    private String name;

    @NotNull
    @Size(min=1, max=8)
    private String abbrevation;

    private boolean successful;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbrevation() {
        return abbrevation;
    }

    public void setAbbrevation(String abbrevation) {
        this.abbrevation = abbrevation;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
