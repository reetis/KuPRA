package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface Products extends CrudRepository<Product, Integer> {
    Product findByName(String name);

}
