package pk.org.rayn.zindagi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;
import pk.org.rayn.zindagi.domain.JwtRequest;
import pk.org.rayn.zindagi.domain.JwtResponse;
import pk.org.rayn.zindagi.domain.ResponseObject;
import pk.org.rayn.zindagi.domain.UserData;
import pk.org.rayn.zindagi.services.JwtService;
import pk.org.rayn.zindagi.services.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true" )
@RestController
public class JwtController {
    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;

    @PostMapping({"/v1/authenticate"})
    public JwtResponse createJwtToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody JwtRequest jwtRequest)
    {
        JwtResponse jwtResponse = jwtService.createJwtToken(jwtRequest);

        Cookie cookie = new Cookie("jwtToken",jwtResponse.jwtToken());
        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);
        return jwtResponse;
    }

    @PreAuthorize("hasAnyRole('User','Admin')")
    @GetMapping({"v1/logout"})
    public Boolean logout(HttpServletRequest request, HttpServletResponse response)
    {
        Cookie[] cookies = request.getCookies();
        for(int i = 0; i< cookies.length ; ++i){
            if(cookies[i].getName().equals("jwtToken")){
                //Cookie cookie = new Cookie("user", cookies[i].getValue());
                //cookie.setMaxAge(0);
                //response.addCookie(cookie);
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
                break;
            }
        }
        return true;
    }

    @GetMapping({"v1/auth/verifyAuth"})
    @PreAuthorize("hasAnyRole('User','Admin','Approver')")
    public Boolean status()
    {
        return true;
    }

    @GetMapping({"/v1/user"})
    @PreAuthorize("hasRole('User')")
    public String user(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        return "user is Authorized";
    }

    @GetMapping({"/review"})
    @PreAuthorize("hasRole('Reviewer')")
    public String review()
    {
        return "Review is Authorized";
    }

}
