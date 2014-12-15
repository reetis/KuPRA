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

    @InitBinder("newMenuItemForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(newMenuItemFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showFridgeContent(MenuListForm form) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());
        Date today = new Date();
        Iterable<Menu> menusList = kupraUsers.findMenuByDate(kupraUser,today, new Date(today.getTime()+60*60*24*1000)); //TODO: Traukiami visi item'ai sutvarkyt traukimus pagal datas

        MenuListDay menuListDay = new MenuListDay();
        menuListDay.setDay(today);
        for(Menu menuItem : menusList){
            MenuListItem menuListItem = new MenuListItem();
            menuListItem.setDateTime(menuItem.getDateTime());
            menuListItem.setRecipeName(menuItem.getRecipe().getName());
            menuListDay.addMenuListItem(menuListItem);
        }
        form.addMenuListDay(menuListDay);
        return "menu";
    }

    @Transactional
    @RequestMapping(value = "/add/{recipeId}", method = RequestMethod.GET)
    public String openSubmitModal(@PathVariable Integer recipeId, final NewMenuItemForm form) {
        form.setRecipeName(recipes.findOne(recipeId).getName());
        Date currentTime = new Date();

        //Add 5 minutes to prevent submitting to past
        form.setDateTime(new Date(currentTime.getTime()+5*60*1000));

        form.setRecipeId(recipeId);
        form.setServings(2);
        return "add-to-menu-modal :: modalBodyFooter";
    }

    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String submit(@Valid final NewMenuItemForm form, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-to-menu-modal :: modalBodyFooter";
        }
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        final eu.komanda30.kupra.entity.Menu newMenuEntity = new Menu();
        newMenuEntity.setRecipe(recipes.findOne(form.getRecipeId()));
        newMenuEntity.setDateTime(form.getDateTime());
        newMenuEntity.setServings(form.getServings());
        kupraUser.addMeniuItem(newMenuEntity);
        kupraUsers.save(kupraUser);

        return "add-to-menu-modal :: modalBodyFooterSuccess";
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
