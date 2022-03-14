package pk.org.rayn.zindagi.configurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pk.org.rayn.zindagi.daos.UserDao;
import pk.org.rayn.zindagi.domain.User;
import pk.org.rayn.zindagi.domain.UserData;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    UserDao userDao;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
            String username = auth.getName();
            String password = auth.getCredentials().toString();

            UserData users = userDao.getUser(username);

            BCryptPasswordEncoder Encoder= new BCryptPasswordEncoder();
            User user = null;
            user = users.user();
            if(!Encoder.matches(password, user.password())) {
                user = null;
            }

            if(user != null)
            {
                List<GrantedAuthority> grantedAuths = new ArrayList<>();
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
                return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
            }
            throw new UsernameNotFoundException("UserName or Password not found");
    }
}
