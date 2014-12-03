package eu.komanda30.kupra.controllers.friendship.notifications_list;

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

@Controller
@RequestMapping("/friends")
public class NotificationListController {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationListController.class);

    @Resource
    private Friendships friendships;

    @RequestMapping(value="/notifications", method = RequestMethod.GET)
    public String showNotificationsList(final NotificationListForm form) {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String username = auth.getName();
        String loggedUserId = username;

        List<Friendship> friendshipsList = friendships.findNotificationsOf(loggedUserId);

        for (Friendship friendship : friendshipsList) {
            NotificationListUnit notificationListUnit = new NotificationListUnit();

            KupraUser kupraUser = friendship.getFriendOf(loggedUserId);

            UserProfile userProfile = kupraUser.getUserProfile();

            notificationListUnit.setName(userProfile.getName());
            notificationListUnit.setSurname(userProfile.getSurname());
            notificationListUnit.setFriendshipId(friendship.getFriendshipId());

            form.addNotificationListUnit(notificationListUnit);
        }
        return "friends_notification_list";
    }
}
