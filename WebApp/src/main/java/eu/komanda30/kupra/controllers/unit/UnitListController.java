package eu.komanda30.kupra.controllers.unit;

import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Units;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/unit-list")
public class UnitListController {

    @Resource
    private Units units;

    @Resource
    private Products products;

    @RequestMapping(method = RequestMethod.GET)
    public String showUnits() {
        return "unit-list";
    }

    @ModelAttribute("units")
    public List<UnitListItem> getUnits() {
        return StreamSupport
                .stream(units.findAll().spliterator(), false)
                .map(unit -> {
                    final UnitListItem unitListItem = new UnitListItem();
                    unitListItem.setIsUsed(products.isUsedUnit(unit));
                    unitListItem.setId(unit.getId());
                    unitListItem.setAbbreviation(unit.getAbbreviation());
                    unitListItem.setName(unit.getName());
                    return unitListItem;
                }).collect(Collectors.toList());
    }
}






