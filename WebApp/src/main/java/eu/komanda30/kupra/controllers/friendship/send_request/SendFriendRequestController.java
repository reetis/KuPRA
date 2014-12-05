package eu.komanda30.kupra.controllers.friendship.send_request;

import eu.komanda30.kupra.entity.Friendship;
import eu.komanda30.kupra.repositories.Friendships;
import eu.komanda30.kupra.repositories.KupraUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by Ignas on 12/5/2014.
 */

@Controller
@RequestMapping("/friends")
public class SendFriendRequestController {
    @Resource
    private Friendships friendships;

    @Resource
    private KupraUsers kupraUsers;

    private static final Logger LOG = LoggerFactory.getLogger(SendFriendRequestController.class);

    @Transactional
    @RequestMapping(value="/sendRequest", method = RequestMethod.POST)
    public String sendRequest(@RequestParam("friend_id") String friendshipId){
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String username = auth.getName();
        String loggedUserId = username;

        Friendship friendship = new Friendship();
        friendship.setTarget(kupraUsers.findOne(friendshipId));
        friendship.setSource(kupraUsers.findByUsername(username));
        friendship.setFriendshipStatus(false);
        friendships.save(friendship);

        return "redirect:/user/" + friendshipId;
    }
}
