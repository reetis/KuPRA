package eu.komanda30.kupra.controllers.unit;

//import eu.komanda30.kupra.repositories.Recipes;
import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.repositories.Units;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @RequestMapping(method = RequestMethod.GET)
    public String showUnits() {

//        Iterable<Unit> units = unitsa.findAll();
        return "unit-list";
    }

    @ModelAttribute("units")
    public Iterable<Unit> getUnits() {
        return this.units.findAll();
    }

    }






