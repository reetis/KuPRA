package eu.komanda30.kupra.controllers.friendship.friends_list;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UserProfile;
import eu.komanda30.kupra.repositories.KupraUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by Ignas on 11/27/2014.
 */
@Controller
@RequestMapping("/friends")
public class FriendsListController {

    private static final Logger LOG = LoggerFactory.getLogger(FriendsListController.class);

    @Resource
    private KupraUsers kupraUsers;

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public String showNewRecipeForm(final FriendListForm form) {
        Iterable<KupraUser> kupraUsersList = kupraUsers.findAll();
        for (KupraUser kupraUser : kupraUsersList) {
            FriendListUnit friendListUnit = new FriendListUnit();
            UserProfile userProfile = kupraUser.getUserProfile();

            friendListUnit.setName(userProfile.getName());
            friendListUnit.setSurname(userProfile.getSurname());

            form.addFriendListUnit(friendListUnit);
        }
        return "friends_list";
    }
}
