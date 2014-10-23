package eu.komanda30.kupra.controllers.unit;

import eu.komanda30.kupra.controllers.registration.RegistrationForm;
import eu.komanda30.kupra.controllers.registration.RegistrationFormValidator;
import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.entity.UserId;
import eu.komanda30.kupra.entity.UserProfile;
import eu.komanda30.kupra.repositories.Units;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid final NewUnitForm form,
                         final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newUnit";
        }

        Unit newUnit = new Unit();
        newUnit.setName(form.getName());
        newUnit.setAbbrevation(form.getAbbrevation());
        units.save(newUnit);
        form.setSuccessful(true);
        return "newUnit";
    }
}
