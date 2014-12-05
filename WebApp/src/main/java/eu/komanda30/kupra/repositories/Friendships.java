package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.Friendship;
import eu.komanda30.kupra.entity.KupraUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Ignas on 11/27/2014.
 */
public interface Friendships extends CrudRepository<Friendship, Integer> {
    @Query("select f from Friendship f join f.target s where (s.id = :user_id or t.id = :user_id) and f.friendshipStatus = true")
    List<Friendship> findFriendsOf(@Param("user_id") String userId);

    @Query("select f from Friendship f join f.target s where (s.id = :user_id or t.id = :user_id) and f.friendshipStatus = false")
    List<Friendship> findNotificationsOf(@Param("user_id") String userId);

    @Query("select count(*) from Friendship where target = :user and friendshipStatus = false")
    int getNotificationCount(@Param("user") KupraUser user);

    @Query("select (count(*) > 0) from Friendship " +
            "where ((source = :user1 and target = :user2) or (source = :user2 and target = :user1)) and friendshipStatus = true")
    boolean isFriends(@Param("user1") KupraUser user, @Param("user2") KupraUser friend);

    Friendship findByUsers(KupraUser source, KupraUser target);
}
