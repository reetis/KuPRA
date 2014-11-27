package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UserId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface KupraUsers extends CrudRepository<KupraUser, UserId> {
    @Query("from KupraUser where userProfile.email = :email")
    KupraUser findByEmail(@Param("email") String email);
}
