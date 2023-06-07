package com.tiendq.cinebooking.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    private final String secretKey;


    public JwtProvider() {
        this.secretKey = "sales-pro";
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000);

        return Jwts.builder()
                .claim("auth", authorities)
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws ServletException {
        String username = "";
        Collection<SimpleGrantedAuthority> roles = null;
        String token = resolveToken(request);

        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            username = body.getSubject();

            String roleBody = (String) body.get("auth");
            List<String> roleString = List.of(roleBody.split(","));
            roles = roleString.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        } catch (JwtException e) {
            throw new ServletException("Invalid JWT token");
        }
        return new UsernamePasswordAuthenticationToken(username, null, roles);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getUserNameFromJwtToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token.substring(7))
                    .getBody()
                    .getSubject();
        }
        return null;
    }
}
