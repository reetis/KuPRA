package eu.komanda30.kupra.controllers.friendship.unfriend;

import eu.komanda30.kupra.entity.Friendship;
import eu.komanda30.kupra.repositories.Friendships;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value="/unfriend", method = RequestMethod.POST)
    public String unfriend(@RequestParam("friendshipId") Integer friendshipId){
        Friendship friendship = friendships.findOne(friendshipId);
        friendships.delete(friendship);

        return "redirect:/friends/list";
    }
}
