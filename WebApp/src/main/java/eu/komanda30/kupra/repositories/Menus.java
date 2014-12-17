package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.Menu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Ignas on 12/15/2014.
 */
public interface Menus extends CrudRepository<Menu, Integer> {
    @Query("select recipe, count(*) AS popularity from Menu menu" +
            " right join menu.recipe recipe" +
            " where recipe.publicAccess = true " +
            " group by recipe order by popularity DESC")
    List<Object> findMostPopular(Pageable pageable);
}
