package eu.komanda30.kupra.controllers.menu;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Menu;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.Recipes;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Gintare on 2014-12-07.
 */
@RequestMapping("/menu")
@Controller
public class MenuController {
    @Resource
    private NewMenuItemFormValidator newMenuItemFormValidator;

    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private Recipes recipes;

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.addValidators(newMenuItemFormValidator);
//    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public String showFridgeContent() {
//        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());
//
////        kupraUser.addMenuItem(menuAddItemForm);

        return "menu";
    }

    @Transactional
    @RequestMapping(value = "/add/{recipeId}", method = RequestMethod.GET)
    public String openSubmitModal(@PathVariable Integer recipeId, final NewMenuItemForm form) {
        form.setRecipeName(recipes.findOne(recipeId).getName());
        form.setDate_time(new Date());
        form.setRecipe_id(recipeId);
        form.setServings(2);
        return "add-to-menu-modal :: modalBody";
    }

    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String submit(@Valid final NewMenuItemForm form, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-to-menu-modal :: modalBody";
        }
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        final eu.komanda30.kupra.entity.Menu newMenuEntity = new Menu();
        newMenuEntity.setRecipe_id(form.getRecipe_id());
        newMenuEntity.setDate_time(form.getDate_time());
        newMenuEntity.setServings(form.getServings());
        kupraUser.addMeniuItem(newMenuEntity);
        kupraUsers.save(kupraUser);

        return "redirect:/menu";
    }

    @ModelAttribute(value = "shortLocale")
    public String shortLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale.getLanguage().equals(new Locale("lt").getLanguage())) {  //TODO: galima bugo vieta (neatitinka localės lt-LT)
            return "lt";
        } else {
            return "en";
        }
    }

}
