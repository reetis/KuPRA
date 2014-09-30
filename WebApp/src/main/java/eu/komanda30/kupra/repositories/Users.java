package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.User;
import eu.komanda30.kupra.entity.UserId;

import org.springframework.data.repository.CrudRepository;

public interface Users extends CrudRepository<User, UserId> {
    User findByEmail(String email);
    User findByUserId(UserId userId);
}
