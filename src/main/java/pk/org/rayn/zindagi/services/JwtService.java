package pk.org.rayn.zindagi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pk.org.rayn.zindagi.configurations.CustomAuthenticationManager;
import pk.org.rayn.zindagi.daos.UserDao;
import pk.org.rayn.zindagi.domain.JwtRequest;
import pk.org.rayn.zindagi.domain.JwtResponse;
import pk.org.rayn.zindagi.domain.User;
import pk.org.rayn.zindagi.domain.UserData;
import pk.org.rayn.zindagi.utils.JwtUtils;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomAuthenticationManager authenticationManager;


    public JwtResponse createJwtToken(JwtRequest jwtRequest)
    {
        String userName = jwtRequest.userName();
        String password = jwtRequest.userPassword();

        if(userName == null || password == null)
            throw new UsernameNotFoundException("Username or Password in missing");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,password));


        UserDetails userDetails = loadUserByUsername(userName);

        String token = jwtUtils.generateToken(userDetails);

        User user = userDao.getUser(userName).user();
        JwtResponse jwtResponse = new JwtResponse(user,token);
        return jwtResponse;
    }




    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       UserData userData = userDao.getUser(s);
       if(userData !=null){
           return new org.springframework.security.core.userdetails.User(
                   userData.user().userName(),
                   userData.user().password(),
                   getAuthorities(userData));
       }
       else {
           throw new UsernameNotFoundException(("UserName not found"));
       }
    }

    private Set getAuthorities(UserData user)
    {
        Set authorities = new HashSet();

        for(int i=0; i< user.roles().size();i++)
        {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+user.roles().get(i)));
        }
        return authorities;
    }
}
