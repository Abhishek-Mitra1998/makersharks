package com.makersharks.identity.util;

import com.makersharks.identity.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class UserUtil {

    /**
     * TODO : An expiry can be added to the token later On
     */

    @Value("${app.secret-key}")
    private String SECRET_KEY;

    public static String ADMIN = "admin@makersharks.com";

    /**
     * Hash function to encrypt the password before saving it to the database
     * @param password
     * @return
     */
    public String generatePasswordHash(String password){
        StringBuilder passBuilder = new StringBuilder();
        for(int i=0;i<password.length();i++){
            char c = password.charAt(i);
            int cInt = (int)c;
            cInt = cInt+5;
            c = (char)cInt;
            passBuilder = passBuilder.append(c);
        }
        return passBuilder.toString();
    }

    /**
     * Function to generate the JWT Token if the user login verified
     * @param existing
     * @return
     */
    public String generateToken(User existing) {

        List<String> roles = Arrays.asList("USER");
        if(existing.getEmail().equals(ADMIN)){
            roles.add("ADMIN");
        }

        byte[] privateKeyDecoded = Base64
                .getDecoder()
                .decode(SECRET_KEY);

        return Jwts.builder()
                .setId(existing.getId())
                .claim("name",existing.getFirstName()+" "+existing.getLastName())
                .claim("email",existing.getEmail())
                .claim("username",existing.getUsername())
                .claim("roles",roles)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(SignatureAlgorithm.HS256,privateKeyDecoded)
                .compact();

    }

    /**
     * Function to test token's validity
     * @param token
     * @param username
     * @return
     */
    public boolean isTokenValid(String token,String username) {

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        if(payload.contains(username)){
            return true;
        }
        return false;

    }
}
