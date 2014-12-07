package eu.komanda30.kupra.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Gintare on 2014-12-07.
 */
@Table(name="menu")
@Entity
@SequenceGenerator(
        name="menuIdSequence",
        sequenceName="menu_seq",
        allocationSize=1)
public class Menu {

        @Id
        @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="unitIdSequence")
        private Integer id;

        @Column(nullable = false)
        private int recipeId;

        @Column(nullable = false)
        private Time time;

        @Column(nullable = false)
        private Date data;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public Date getData() {
                return data;
        }

        public void setData(Date data) {
                this.data = data;
        }

        public Time getTime() {
                return time;
        }

        public void setTime(Time time) {
                this.time = time;
        }

        public int getRecipeId() {
                return recipeId;
        }

        public void setRecipeId(int recipeId) {
                this.recipeId = recipeId;
        }
}


