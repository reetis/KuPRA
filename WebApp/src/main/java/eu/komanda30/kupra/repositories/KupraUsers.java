package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.KupraUser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface KupraUsers extends CrudRepository<KupraUser, String> {
    @Query("from KupraUser where profile.email = :email")
    KupraUser findByEmail(@Param("email") String email);

    @Query("from KupraUser where usernamePasswordAuth.username = :username")
    KupraUser findByUsername(@Param("username") String username);
}
