package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.UsernamePasswordAuth;

import org.springframework.data.repository.CrudRepository;

public interface UsernamePasswordAuths extends CrudRepository<UsernamePasswordAuth, String> {
}
