package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.Fridge;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Recipe;
import eu.komanda30.kupra.entity.UserId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Lukas on 2014.12.02.
 */
public interface Fridges extends CrudRepository<Fridge, Integer> {

}
