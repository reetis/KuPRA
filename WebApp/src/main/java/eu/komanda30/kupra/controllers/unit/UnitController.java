package eu.komanda30.kupra.controllers.unit;

import eu.komanda30.kupra.services.ProductManager;

import javax.annotation.Resource;
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
    private ProductManager productManager;

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

        productManager.addProductUnit(form.getName(), form.getAbbreviation());
        form.setSuccessful(true);
        return "newUnit";
    }
}
