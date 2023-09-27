package com.minsait.treinamento.utils.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.minsait.treinamento.dtos.security.token.JWTDataDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTUtil {
    
    public static String generateToken(JWTDataDTO data, String username, Collection<? extends GrantedAuthority> cargos, Map<String,Object> claimsProperties) {
        
        Claims claims = Jwts.claims().setSubject(username);
        
        claims.put("cargos", cargos);
        
        if( claimsProperties != null) {
            claims.putAll(claimsProperties);
        }
                
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + data.getExpiration()))
                .signWith(SignatureAlgorithm.HS512, data.getSecret().getBytes())
                .compact();
    }
    
//  public static boolean tokenValido(String token, String secret) throws UnsupportedJwtException,
//                                                                        MalformedJwtException,
//                                                                        SignatureException,
//                                                                        ExpiredJwtException,
//                                                                        IllegalArgumentException {
//      
//      Claims claims = getClaims(token, secret);
//      if (claims != null) {
//          String username = claims.getSubject();
//          Date expirationDate = claims.getExpiration();
//          Date now = new Date(System.currentTimeMillis());
//          if (username != null && expirationDate != null && now.before(expirationDate)) {
//              return true;
//          }
//      }
//      return false;
//  }

    public static boolean checkToken(String token, String secret) {
        try {
            JWTUtil.getUsername(token, secret);
        }
        catch (MalformedJwtException | IllegalArgumentException | SignatureException | UnsupportedJwtException  ex) {
            return false;
        }
        catch(ExpiredJwtException ex) {
            return true;
        }
        return true;
        
    }
    
    public static String getUsername(String token, String secret) throws UnsupportedJwtException,
                                                                         MalformedJwtException,
                                                                         SignatureException,
                                                                         ExpiredJwtException,
                                                                         IllegalArgumentException {
        Claims claims = getClaims(token, secret);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }
    
    private static Claims getClaims(String token, String secret) throws UnsupportedJwtException,
                                                                        MalformedJwtException,
                                                                        SignatureException,
                                                                        ExpiredJwtException,
                                                                        IllegalArgumentException  {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }
}
