package eu.komanda30.kupra.entity;

import javax.persistence.*;

@Table(name="product")
@Entity
@SequenceGenerator(
        name="productIdSequence",
        sequenceName="product_seq",
        allocationSize=1
)
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="productIdSequence")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @ManyToOne
    private Unit unit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
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


