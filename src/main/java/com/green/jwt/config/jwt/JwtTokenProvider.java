package com.green.jwt.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.green.jwt.config.JwtConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class JwtTokenProvider {
    private final ObjectMapper objectMapper;
    private final JwtConst jwtConst;
    private final SecretKey secretKey;

    public JwtTokenProvider(ObjectMapper objectMapper, JwtConst jwtConst) {
        this.objectMapper = objectMapper;
        this.jwtConst = jwtConst;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConst.getSecret()));
    }

    public String generateAccessToken(JwtUser jwtUser) {
        return generateToken(jwtUser, jwtConst.getAccessTokenExpiry());
    }

    public String generateRefreshToken(JwtUser jwtUser) {
        return generateToken(jwtUser, jwtConst.getRefreshTokenExpiry());
    }

    public String generateToken(JwtUser jwtUser, long tokenValidMilliSecond) {
        Date now = new Date();

        return Jwts.builder()
                .header().type(jwtConst.getTokenName())
                .and()

                .issuer(jwtConst.getIssuer())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + tokenValidMilliSecond))
                .claim(jwtConst.getClaimKey(), makeClaimByUserToString(jwtUser))

                .signWith(secretKey)
                .compact();
    }

    // 객체 > String : 직렬화(JSON)
    private String makeClaimByUserToString(JwtUser jwtUser) {
        //객체 자체를 JWT에 담고 싶어서 객체를 직렬화
        //jwtUser에 담고있는 데이터를 JSON형태의 문자열로 변환
        try {
            return objectMapper.writeValueAsString(jwtUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //------- 만들어진 토큰(AT, RT)
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(jwtConst.getHeaderSchemaName());

        if(bearerToken == null || !bearerToken.startsWith(jwtConst.getTokenType())) {
            return null;
        }

        // 토큰이 있고, Bearer로 문자열이 시작한다. 그러면 Bearer 내용을 제외한 토근값만 리턴한다.

        return bearerToken.substring(jwtConst.getTokenType().length() + 1); // Bearer(빈칸)까지 index를 설정해야 하기 때문
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public JwtUser getJwtUserFromToken(String token) {
        Claims claims = getClaims(token);
        String json = claims.get(jwtConst.getClaimKey(), String.class);

        try {
            return objectMapper.readValue(json, JwtUser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Authentication getAuthentication(String token) {
        try {
            JwtUser jwtUser = getJwtUserFromToken(token);
            return new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
        } catch(Exception e) {
            return null;
        }
    }
}
