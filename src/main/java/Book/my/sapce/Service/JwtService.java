package Book.my.sapce.Service;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
@Service
public class JwtService {

    private final String SECRET_KEY="myverysecretkeymyverysecretkeymyverysecretkey";   //my-super-secret-key-for-jwt-256

    private final Key key=Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

// token generate cheyyunnath
    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60)) //login time to + token expiry time sett
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey)key)       //claims token
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


}
