package pk.org.rayn.zindagi.configurations;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pk.org.rayn.zindagi.services.JwtService;
import pk.org.rayn.zindagi.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {


        if(httpServletRequest.getMethod().equals("OPTIONS"))
        {
            UserDetails userDetails = jwtService.loadUserByUsername("admin");
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,null,
                            userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        String jwtToken = null;
        String userName= null;
        if(httpServletRequest.getCookies() != null) {
            jwtToken = //"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY0NjU1MTMzOCwiaWF0IjoxNjQ2NDY0OTM4fQ.E_ZR7KrTjJWjtngMGcYINSC4BwiZfjK2UMtkUI4jptDElXYzMzHrv-YmP9m1bwtAlYmlJrGnclG-Oe22uFOfdA";
             httpServletRequest.getCookies()[0].getValue();

            try {
                userName = jwtUtils.getUserNameFromToken(jwtToken);
            }
            catch (IllegalArgumentException e) {
                System.out.println("Error");
            }
            catch (ExpiredJwtException e)
            {
                System.out.println("Expired");
            }
        }

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtService.loadUserByUsername(userName);

            if(jwtUtils.validateToken(jwtToken,userDetails))
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,null,
                                userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
