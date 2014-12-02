package eu.komanda30.kupra.entity;

import javax.persistence.*;

/**
 * Created by Lukas on 2014.12.02.
 */


@Table(name="fridge")
@Entity
@SequenceGenerator(
        name="fridgeIdSequence",
        sequenceName="fridge_seq",
        allocationSize=1)
public class Fridge {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="fridgeIdSequence")
    private int id;

    private String userId;

    private int productId;

    private double amount;
}
