package eu.komanda30.kupra.controllers.product;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gintare on 2014-11-27.
 */
public class NewProductForm {

    @Min(0)
    private int selectedUnitId;

    @Size(min = 1, max = 256)
    private String description;

    @Size(min = 1, max = 50)
    private String name;

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
