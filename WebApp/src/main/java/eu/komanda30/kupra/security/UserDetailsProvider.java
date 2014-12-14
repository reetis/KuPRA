package eu.komanda30.kupra.security;

import eu.komanda30.kupra.entity.KupraUser;
import eu.komanda30.kupra.entity.UsernamePasswordAuth;
import eu.komanda30.kupra.repositories.KupraUsers;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;

@Service
public class UserDetailsProvider implements UserDetailsService {
    @Resource
    private KupraUsers kupraUsers;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final KupraUser kupraUser = kupraUsers.findByUsername(username);
        if (kupraUser == null) {
            throw new UsernameNotFoundException("Username not found!");
        }

        final UsernamePasswordAuth auth = kupraUser.getUsernamePasswordAuth();
        return new User(auth.getUsername(), auth.getPassword(),
                buildAuthorities(kupraUser.isAdmin()));
    }

    public Set<GrantedAuthority> buildAuthorities(boolean isAdmin) {
        final ImmutableSet.Builder<GrantedAuthority> builder
                = new ImmutableSet.Builder<>();
        builder.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (isAdmin) {
            builder.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return builder.build();
    }
}
