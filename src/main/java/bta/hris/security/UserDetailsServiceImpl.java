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

import java.util.HashSet;
import java.util.Set;

@Service
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

        return new User(user.getNip(), user.getPassword(), grantedAuthorities);
    }
}
