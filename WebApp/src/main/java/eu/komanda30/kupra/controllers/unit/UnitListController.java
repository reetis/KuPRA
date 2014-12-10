package eu.komanda30.kupra.controllers.unit;

//import eu.komanda30.kupra.repositories.Recipes;

import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Units;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by Gintare on 2014-12-02.
 */
@Controller
@RequestMapping("/unit-list")
public class UnitListController {

    @Resource
    private Units units;

    @Resource
    private Products products;

    @RequestMapping(method = RequestMethod.GET)
    public String showUnits(UnitsListForm form) {
        Iterable<Unit> unitsList = units.findAll();

        for(Unit unit : unitsList){
            UnitListItem unitListItem = new UnitListItem();
            unitListItem.setIsUsed(products.isUsedUnit(unit));
            unitListItem.setId(unit.getId());
            unitListItem.setAbbreviation(unit.getAbbreviation());
            unitListItem.setName(unit.getName());
            form.addUnitListItem(unitListItem);
        }
        return "unit-list";
    }

//    @ModelAttribute("units")
//    public Iterable<Unit> getUnits() {
//        return this.units.findAll();
//    }


    }






