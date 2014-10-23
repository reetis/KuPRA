package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Ignas on 10/23/2014.
 */
public interface Recipes extends CrudRepository<Recipe, Integer> {

}
