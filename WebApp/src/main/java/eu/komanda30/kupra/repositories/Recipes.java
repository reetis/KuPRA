package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.Recipe;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Ignas on 10/23/2014.
 */
public interface Recipes extends CrudRepository<Recipe, Integer> {
    @Query("from Recipe where publicAccess = true")
    Iterable<Recipe> findByPublic();

    @Query("from Recipe where author = :user")
    Iterable<Recipe> findByUser(@Param("user") String user);

    @Query("from Recipe where author = :user and publicAccess = true")
    Iterable<Recipe> findByUserPublic(@Param("user") String user);
}
