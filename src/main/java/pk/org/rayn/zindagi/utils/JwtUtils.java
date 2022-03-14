package pk.org.rayn.zindagi.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    static final String SECRET_KEY="MY_SECRET_KEY";
    // sec * min * hour = time in seconds
    static final  Integer TOKEN_VALIDATY = 60*60*24;


    public String getUserNameFromToken(String token)
    {
        return getClaimFromToken(token,Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        if(!    isTokenExpired(token))
        {
            String userName = getClaimFromToken(token,Claims::getSubject);
            return userName.equals(userDetails.getUsername());
        }
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDATY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    private<T> T getClaimFromToken(String token, Function<Claims,T> callback)
    {
        final Claims claims = getAllClaimsFromToken(token);
        return callback.apply(claims);

    }

    private Claims getAllClaimsFromToken(String token)
    {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        final Date expireDate = getExpirationDateFromToken(token);
        return expireDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token,Claims::getExpiration);
    }


}
