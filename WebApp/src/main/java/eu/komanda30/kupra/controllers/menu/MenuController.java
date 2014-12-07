package eu.komanda30.kupra.controllers.menu;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.repositories.KupraUsers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.awt.*;

/**
 * Created by Gintare on 2014-12-07.
 */
@RequestMapping("/menu")
@Controller
public class MenuController {

    @Resource
    private KupraUsers kupraUsers;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public String showFridgeContent(final MenuItemList list, final MenuAddItemForm menuAddItemForm) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

//        kupraUser.addMenuItem(menuAddItemForm);

        return "menu";
    }

}
