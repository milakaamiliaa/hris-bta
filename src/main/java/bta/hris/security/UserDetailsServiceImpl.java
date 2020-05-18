package bta.hris.security;

import bta.hris.model.UserModel;
import bta.hris.repository.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDB userDb;

    @Override
    public UserDetails loadUserByUsername(String nip) throws UsernameNotFoundException {
        UserModel user = userDb.findByNip(nip);

        if (user == null) {
            throw new UsernameNotFoundException("Not found!");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getNama()));

//        return new User(user.getNip(), user.getPassword(), grantedAuthorities);
        return buildUserForAuthentication(user, grantedAuthorities);
    }

    private User buildUserForAuthentication(UserModel user,  Set<GrantedAuthority> authorities) {
        String username = user.getNip();
        String password = user.getPassword();
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        CurrentUser currentUser = new CurrentUser(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        currentUser.setNama(user.getNama());

        return currentUser;
    }
}
