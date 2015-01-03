package eu.komanda30.kupra.controllers.editproductpopup;

import org.apache.avro.reflect.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ProductEditForm {

    @Nullable
    private Integer productId;

    @Min(0)
    private int selectedUnitId;

    private String unitName;

    @Size(max = 256)
    private String description;

    @Size(min = 1, max = 50)
    private String name;

    @Nullable
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(@Nullable Integer productId) {
        this.productId = productId;
    }

    public boolean isEditForm() {
        return productId != null;
    }

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
