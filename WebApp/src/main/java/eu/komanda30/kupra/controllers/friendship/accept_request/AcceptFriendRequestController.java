package eu.komanda30.kupra.controllers.friendship.accept_request;

import eu.komanda30.kupra.entity.Friendship;
import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.repositories.Friendships;
import eu.komanda30.kupra.repositories.KupraUsers;

import javax.annotation.Resource;

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

@Controller
@RequestMapping("/friends")
public class AcceptFriendRequestController {

    private static final Logger LOG = LoggerFactory.getLogger(AcceptFriendRequestController.class);
    @Resource
    private Friendships friendships;
    @Resource
    private KupraUsers kupraUsers;

    @ResponseBody
    @Transactional
    @RequestMapping(value="/accept_request", method = RequestMethod.POST)
    public String confirmRequest(@RequestParam("source_id") String sourceId){
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        KupraUser loggedUser = kupraUsers.findByUsername(auth.getName());

        Friendship friendship = friendships.findByUsers(kupraUsers.findOne(sourceId), loggedUser);

        friendship.setFriendshipStatus(true);
        Friendship secondLink = new Friendship();
        secondLink.setFriendshipStatus(true);
        secondLink.setSource(friendship.getTarget());
        secondLink.setTarget(friendship.getSource());

        friendships.save(secondLink);

        return "true";
    }

}
