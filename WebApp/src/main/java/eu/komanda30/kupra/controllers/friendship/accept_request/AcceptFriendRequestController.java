package eu.komanda30.kupra.controllers.friendship.accept_request;

import eu.komanda30.kupra.entity.Friendship;
import eu.komanda30.kupra.repositories.Friendships;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by Ignas on 11/30/2014.
 */

@Controller
@RequestMapping("/friends")
public class AcceptFriendRequestController {

    @Resource
    private Friendships friendships;

    private static final Logger LOG = LoggerFactory.getLogger(AcceptFriendRequestController.class);

    @Transactional
    @RequestMapping(value="/accept_request", method = RequestMethod.POST)
    public String confirmRequest(@RequestParam("friendshipId") Integer friendshipId){
        Friendship friendship = friendships.findOne(friendshipId);
        friendship.setFriendshipStatus(true);
        Friendship secondLink = new Friendship();
        secondLink.setFriendshipStatus(true);
        secondLink.setSource(friendship.getTarget());
        secondLink.setTarget(friendship.getSource());
        friendships.save(secondLink);

        return "redirect:/friends/notifications";
    }

}
