package eu.komanda30.kupra.controllers.editunitpopup;

import eu.komanda30.kupra.entity.Unit;
import eu.komanda30.kupra.repositories.Units;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/unit-edit-popup")
public class UnitEditPopupController {
    @Resource
    private UnitEditFormValidator unitEditFormValidator;

    @Resource
    private Units units;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(unitEditFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showNewUnitForm(final UnitEditForm form) {
        return "popups/unit :: unitEditForm";
    }

    @RequestMapping(value="/{unitId}",method = RequestMethod.GET)
    public String showEditUnitForm(
            @PathVariable("unitId") int unitId,
            final UnitEditForm form,
            final HttpServletRequest request) {
        final Unit unit = units.findOne(unitId);
        Assert.notNull(unit);

        form.setUnitId(unit.getId());
        form.setName(unit.getName());
        form.setAbbreviation(unit.getAbbreviation());

        return "popups/unit :: unitEditForm";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(@Valid final UnitEditForm form,
                         final BindingResult bindingResult,
                         final HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "popups/unit :: unitEditForm";
        }

        final Unit unit;
        if (form.isEditForm()) {
            Assert.state(request.isUserInRole("ROLE_ADMIN"));
            unit = units.findOne(form.getUnitId());
        } else {
            unit = new Unit();
        }

        unit.setName(form.getName());
        unit.setAbbreviation(form.getAbbreviation());
        units.save(unit);

        return "popups/unit :: unitSavedForm";
    }
}
