package eu.komanda30.kupra.controllers.friendship.unfriend;

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

@Controller
@RequestMapping("/friends")
public class UnFriendController {

    @Resource
    private Friendships friendships;

    private static final Logger LOG = LoggerFactory.getLogger(UnFriendController.class);

    @Transactional
    @RequestMapping(value="/unfriend", method = RequestMethod.POST)
    public String unfriend(@RequestParam("friendshipId") Integer friendshipId,
                           @RequestParam("denyAction") Integer denyAction){

        Friendship friendship = friendships.findOne(friendshipId);
        if (friendship.isFriendshipStatus()){
            Friendship secondLink = friendships.findByUsers(friendship.getTarget(), friendship.getSource());
            friendships.delete(secondLink);
        }

        friendships.delete(friendship);

        String redirectUrl = (denyAction != null && denyAction == 1) ?
                "redirect:/friends/notifications" :
                "redirect:/friends/list";

        return redirectUrl;
    }
}
