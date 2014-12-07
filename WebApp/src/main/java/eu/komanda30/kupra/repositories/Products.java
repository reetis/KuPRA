package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.Product;
import eu.komanda30.kupra.entity.Unit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface Products extends CrudRepository<Product, Integer> {
    Product findByName(String name);

    @Query("select (count(*) > 0) from Product p where p.unit = :unit")
    Boolean isUsedUnit(@Param("unit") Unit unit);

}
