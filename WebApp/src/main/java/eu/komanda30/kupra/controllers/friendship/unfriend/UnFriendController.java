package eu.komanda30.kupra.controllers.friendship.unfriend;

import eu.komanda30.kupra.entity.Friendship;
import eu.komanda30.kupra.entity.KupraUser;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/friends")
public class UnFriendController {

    @Resource
    private Friendships friendships;

    @Resource
    private KupraUsers kupraUsers;

    private static final Logger LOG = LoggerFactory.getLogger(UnFriendController.class);

    @ResponseBody
    @Transactional
    @RequestMapping(value="/unfriend", method = RequestMethod.POST)
    public String unfriend(@RequestParam("source_id") String sourceId){
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        KupraUser loggedUser = kupraUsers.findByUsername(auth.getName());

        Friendship friendship = friendships.findByUsers(kupraUsers.findOne(sourceId), loggedUser);
        if (friendship.isFriendshipStatus()){
            Friendship secondLink = friendships.findByUsers(friendship.getTarget(), friendship.getSource());
            if (secondLink != null){
                friendships.delete(secondLink);
            }
        }

        friendships.delete(friendship);

        return "true";
    }
}
