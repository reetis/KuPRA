package eu.komanda30.kupra.repositories;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.entity.UnitId;
import eu.komanda30.kupra.entity.UserId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface Units extends CrudRepository<Unit, UnitId> {
    Unit findByName(String name);
    Unit findByAbbrevation(String abbrevation);
}
