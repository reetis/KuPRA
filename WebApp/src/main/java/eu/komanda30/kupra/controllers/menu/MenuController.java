package eu.komanda30.kupra.controllers.menu;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.Menu;
import eu.komanda30.kupra.repositories.KupraUsers;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/menu")
@Controller
public class MenuController {

    @Resource
    private KupraUsers kupraUsers;

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
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@Valid final MenuItemForm form) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        final eu.komanda30.kupra.entity.Menu newMenuEntity = new Menu();
        newMenuEntity.setRecipe_id(form.getRecipe_id());
        newMenuEntity.setDate_time(form.getDate_time());
        kupraUser.addMeniuItem(newMenuEntity);
        kupraUsers.save(kupraUser);

        return "addToMenu :: receptSavedForm";
    }

}
