package eu.komanda30.kupra.controllers.menu;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Menu;
import eu.komanda30.kupra.repositories.KupraUsers;
import eu.komanda30.kupra.repositories.Menus;
import eu.komanda30.kupra.repositories.Recipes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Gintare on 2014-12-07.
 */
@RequestMapping("/menu")
@Controller
public class MenuController {

    private final static Logger LOG = LoggerFactory.getLogger(MenuController.class);

    @Resource
    private NewMenuItemFormValidator newMenuItemFormValidator;

    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private Menus menus;

    @Resource
    private Recipes recipes;

    @InitBinder("newMenuItemForm")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(newMenuItemFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showFridgeContent(MenuListForm form,
                                    @RequestParam(value = "dateFrom", required = false) final String newDateFrom) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        String templateToRender = "menu";

        Date dateFrom = null;
        if (newDateFrom != null){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                dateFrom = format.parse(newDateFrom);
                templateToRender = "menu :: menuContainer";
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Calendar c = new GregorianCalendar();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);

            dateFrom = c.getTime();
        }

        // Hardcoded 4 Collumns now
        for(int i=0; i<4; i++){
            // Adding 24 hour's to from date
            Date dateTo = new Date(dateFrom.getTime()+60*60*24*1000);
            Iterable<Menu> menusList = kupraUsers.findMenuByDate(kupraUser, dateFrom, dateTo);

            MenuListDay menuListDay = new MenuListDay();
            menuListDay.setDay(dateFrom);
            for(Menu menuItem : menusList){
                MenuListItem menuListItem = new MenuListItem();
                menuListItem.setDateTime(menuItem.getDateTime());
                menuListItem.setRecipeName(menuItem.getRecipe().getName());
                menuListItem.setMenuItemId(menuItem.getId());
                menuListDay.addMenuListItem(menuListItem);
            }
            form.addMenuListDay(menuListDay);
            dateFrom = dateTo;
        }

        return templateToRender;
    }

    @Transactional
    @RequestMapping(value = "/cook/{menuItem}", method = RequestMethod.POST)
    public String openCookModal(final RecipeCookForm form, @PathVariable Integer menuItem) {
        Menu menu = menus.findOne(menuItem);
        KupraUser kupraUser = kupraUsers.findByMenu(menu);

        // Manage Menu entity
        menu.setDateTime(form.getDateTime());
        menu.setCompleted(true);
        menu.setScore(form.getScore());
        menu.setServings(form.getServings());

        //Manage Fridge
        if (kupraUser.consumeItemsFromFridge(menu)) {
            kupraUsers.save(kupraUser);
        }

        return "popups/cookRecipe :: menuCookedModal";
    }

    @Transactional
    @RequestMapping(value = "/review/{menuItemId}", method = RequestMethod.GET)
    public String openReviewModal(@PathVariable Integer menuItemId, final RecipeCookForm form) {
        Menu menu = menus.findOne(menuItemId);
        form.setName(menu.getRecipe().getName());
        form.setDateTime(menu.getDateTime());
        form.setMenuItemId(menu.getId());
        form.setServings(menu.getServings());
        form.setScore(10);

        return "popups/cookRecipe :: menuCookModal";
    }

    @ResponseBody
    @Transactional
    @RequestMapping(value = "/remove/{menuItemId}", method = RequestMethod.POST)
    public String removeMenuItem(@PathVariable Integer menuItemId) {
        Menu menu = menus.findOne(menuItemId);
        KupraUser owner = kupraUsers.findByMenu(menu);
        owner.removeMenuItem(menu);
        kupraUsers.save(owner);

        return "";
    }



    @Transactional
    @RequestMapping(value = "/add/{recipeId}", method = RequestMethod.GET)
    public String openSubmitModal(@PathVariable Integer recipeId, final NewMenuItemForm form) {
        form.setRecipeName(recipes.findOne(recipeId).getName());
        Date currentTime = new Date();

        //Add 5 minutes to prevent submitting to past
        form.setDateTime(new Date(currentTime.getTime()+5*60*1000));

        form.setRecipeId(recipeId);
        form.setServings(1);
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
