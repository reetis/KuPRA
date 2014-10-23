package eu.komanda30.kupra.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Rytis on 2014-10-21.
 */

@Table(name="unit")
@Entity
public class Unit {
    @Id
    private UnitId unitId;

    private String name;
    private String abbrevation;

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
}
