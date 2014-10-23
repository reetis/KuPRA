package eu.komanda30.kupra.services.impl;

import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.repositories.Units;
import eu.komanda30.kupra.services.ProductManager;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class ProductManagerImpl implements ProductManager {
    @Resource
    private Units units;

    @Transactional
    @Override
    public void addProductUnit(String name, String abbreviation) {
        final Unit newUnit = new Unit();
        newUnit.setName(name);
        newUnit.setAbbreviation(abbreviation);
        units.save(newUnit);
    }
}
