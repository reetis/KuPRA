package eu.komanda30.kupra.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Rytis on 2014-10-21.
 */
@Embeddable
public class UnitId implements Serializable{
    private int unitId;

    protected UnitId() {
    }

    public UnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
}
