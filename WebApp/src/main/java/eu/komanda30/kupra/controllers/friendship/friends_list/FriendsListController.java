package eu.komanda30.kupra.controllers.friendship.friends_list;

import eu.komanda30.kupra.entity.Friendship;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UserProfile;
import eu.komanda30.kupra.repositories.Friendships;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Ignas on 11/27/2014.
 */
@Controller
@RequestMapping("/friends")
public class FriendsListController {

    private static final Logger LOG = LoggerFactory.getLogger(FriendsListController.class);

    @Resource
    private Friendships friendships;

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public String showFriendsList(final FriendListForm form) {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String username = auth.getName();
        String loggedUserId = username;

        List<Friendship> friendshipsList = friendships.findFriendsOf(loggedUserId);

        for (Friendship friendship : friendshipsList) {
            FriendListUnit friendListUnit = new FriendListUnit();

            KupraUser kupraUser = friendship.getFriendOf(loggedUserId);

            UserProfile userProfile = kupraUser.getUserProfile();

            friendListUnit.setName(userProfile.getName());
            friendListUnit.setSurname(userProfile.getSurname());
            friendListUnit.setFriendshipId(friendship.getFriendshipId());

            form.addFriendListUnit(friendListUnit);
        }
        return "friends_list";
    }
}
