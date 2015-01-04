package eu.komanda30.kupra.controllers.unit;

import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Units;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/units")
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
                .stream(units.findByOrderByNameAsc().spliterator(), false)
                .map(unit -> {
                    final UnitListItem unitListItem = new UnitListItem();
                    unitListItem.setIsUsed(products.isUsedUnit(unit));
                    unitListItem.setId(unit.getId());
                    unitListItem.setAbbreviation(unit.getAbbreviation());
                    unitListItem.setName(unit.getName());
                    return unitListItem;
                }).collect(Collectors.toList());
    }

    @ResponseBody
    @RequestMapping(value="/{unitId}", method = RequestMethod.DELETE)
    @Transactional
    public String deleteUnit(@PathVariable("unitId") Integer unitId) {
        final Unit unit = units.findOne(unitId);

        if (unit == null) {
            return Boolean.TRUE.toString();
        }

        if (products.isUsedUnit(unit)) {
            return Boolean.FALSE.toString();
        }

        units.delete(unit);
        return Boolean.TRUE.toString();
    }
}






