package eu.komanda30.kupra.controllers.unit.remove;

import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.repositories.Products;
import eu.komanda30.kupra.repositories.Units;

import javax.annotation.Resource;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Ignas on 12/7/2014.
 */
@Controller
@RequestMapping("/unit")
public class RemoveUnitController {

    @Resource
    private Products products;

    @Resource
    private Units units;

    @Secured("ADMIN")
    @ResponseBody
    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public String deleteUnit(@RequestParam("unit_id") Integer unitId) {
        Unit unit = units.findOne(unitId);
        if (unit != null){
            if (products.isUsedUnit(unit)){
                return "false";
            }else{
                units.delete(unit);
            }
        }

        return "";
    }
}
