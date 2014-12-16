package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface KupraUsers extends CrudRepository<KupraUser, String> {
    @Query("from KupraUser where profile.email = :email")
    KupraUser findByEmail(@Param("email") String email);

    @Query("from KupraUser where usernamePasswordAuth.username = :username")
    KupraUser findByUsername(@Param("username") String username);

    @Query("from KupraUser "
            + "where usernamePasswordAuth.resetPasswordToken = :token "
            + "and usernamePasswordAuth.resetPasswordTokenValidTill > current_date")
    KupraUser findByPasswordResetToken(@Param("token") String token);

    @Query("select m from KupraUser u left join u.menuList m where u = :user and m.dateTime between :fromDate and :toDate order by m.dateTime asc")
    List<Menu> findMenuByDate(@Param("user") KupraUser user, @Param("fromDate") Date dateFromString, @Param("toDate") Date dateToString);

    @Query("select u from KupraUser u join u.menuList m where m = :menu")
    KupraUser findByMenu(@Param("menu") Menu menu);
}
