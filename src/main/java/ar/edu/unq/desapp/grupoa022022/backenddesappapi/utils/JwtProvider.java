package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;

import ar.edu.unq.desapp.grupoa022022.backenddesappapi.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider{
    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(User user){
        Map<String, Object> claims;

        claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("id", user.getId());
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);//setea la duración del jwt en una hora


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public boolean validate (String token){
        try{
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;

        }
    }

    public String getEmailFromToken(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch(Exception e){
            return "Bad token";
        }
    }
}