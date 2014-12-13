package eu.komanda30.kupra.controllers.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class NewProductForm {

    @Min(0)
    private int selectedUnitId;

    @Size(min = 1, max = 256)
    private String description;

    @Size(min = 1, max = 50)
    private String name;


    private String unitName;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getSelectedUnitId() {
        return selectedUnitId;
    }

    public void setSelectedUnitId(int selectedUnitId) {
        this.selectedUnitId = selectedUnitId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
