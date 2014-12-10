package eu.komanda30.kupra.controllers.unit;

import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.repositories.Units;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Rytis on 2014-10-21.
 */
@Controller
@RequestMapping("/unit")
public class UnitController {
    @Resource
    private NewUnitFormValidator newUnitFormValidator;

    @Resource
    private Units units;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(newUnitFormValidator);
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String showUnitFrame(final NewUnitForm form) {
        return "newUnit :: newUnitForm";
    }

    @Transactional
    @RequestMapping(value="add", method = RequestMethod.POST)
    public String submit(@Valid final NewUnitForm form,
                         final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newUnit :: newUnitForm";
        }

        final Unit newUnit = new Unit();
        newUnit.setName(form.getName());
        newUnit.setAbbreviation(form.getAbbreviation());
        units.save(newUnit);

        return "newUnit :: unitSavedForm";
    }
}
