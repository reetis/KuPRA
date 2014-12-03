package eu.komanda30.kupra.controllers.sessiondata;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.repositories.Friendships;
import eu.komanda30.kupra.repositories.KupraUsers;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("sessionData")
public class SessionDataProvider {
    @Resource
    private KupraUsers kupraUsers;

    @Resource
    private Friendships friendships;

    public UserDetails getLoggedUserDetails() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String username = auth.getName();
        final KupraUser user = kupraUsers.findOne(username);

        final UserDetails result = new UserDetails();
        result.setName(user.getUserProfile().getName());
        result.setSurname(user.getUserProfile().getSurname());
        result.setAdmin(isLoggedUserAdmin());
        return result;
    }

    public boolean isLoggedUserAdmin() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

    public int getNotificationCount(){
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String username = auth.getName();
        return friendships.getNotificationCount(kupraUsers.findOne(username));
    }
}
