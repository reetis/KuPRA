package eu.komanda30.kupra.controllers.profilelist;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UserProfileImage;
import eu.komanda30.kupra.repositories.Friendships;
import eu.komanda30.kupra.repositories.KupraUsers;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profiles")
public class ProfileListController {

    @Resource
    private Friendships friendships;

    @Resource
    private KupraUsers kupraUsers;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public String showProfilesDefault(final ProfileList list, Model model){
        return showProfileList("all", list, model);
    }

    @Transactional
    @RequestMapping(value = "/{scope}", method = RequestMethod.GET)
    public String showProfileList(@PathVariable String scope, final ProfileList list, Model model) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUserId = auth.getName();

        List<KupraUser> users;

        switch (scope) {
            case "all":
                users = kupraUsers.findAllList();
                break;
            case "friends":
                users = friendships.findFriendsOf(loggedUserId).stream()
                            .map(x -> x.getFriendOf(loggedUserId))
                            .collect(Collectors.toList());
                break;
            case "invites":
                users = friendships.findNotificationsOf(loggedUserId).stream()
                            .map(x -> x.getFriendOf(loggedUserId))
                            .collect(Collectors.toList());
                break;
            default:
                throw new ResourceNotFoundException();
        }

        users.stream()
                .map(this::kupraUserToUserUnit)
                .forEach(list::addUser);

        if (scope.equals("all")) {
            list.sort();
        }
        model.addAttribute("section", scope);
        return "profile-list";
    }

    private ProfileList.User kupraUserToUserUnit(KupraUser user) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final KupraUser kupraUser = kupraUsers.findByUsername(auth.getName());

        ProfileList.User userEntry = new ProfileList.User();
        userEntry.setUserId(user.getUserId());
        if (friendships.isFriends(kupraUser, user) || user.equals(kupraUser)) {
            userEntry.setDisplayName(user.getProfile().getFullName());
            userEntry.setImageUrl(user.getProfile().getMainPhoto()
                    .map(UserProfileImage::getThumbUrl)
                    .orElse(null));
        } else {
            userEntry.setDisplayName(user.getUserId());
        }
        return userEntry;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {}
}
