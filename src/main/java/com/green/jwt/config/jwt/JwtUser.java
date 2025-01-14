package com.green.jwt.config.jwt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@EqualsAndHashCode
public class JwtUser implements UserDetails {
    private long signedUserId;
    private List<String> roles; // 인가(권한)처리 때 사용, ROLE_이름, ROLE_USER, ROLE_ADMIN

    // 리턴타입으로 콜렉션인데 콜렉션에 방 하나하나의 타입은 <> 지정을 한다.
    // <?>이렇게 하면 Object가 되기 때문에 모든 타입이 허용이 된다.
    // <?, extends GrantedAuthority>는 지정 타입이 꼭 GrantAuthority를 상속받은 객체만 가능하도록 제한을 거는 것

    // <?, super GrantedAuthority>는 지정 타입이 꼭 GrantAuthority포함, GrantedAuthority의 부모 객체만 가능하도록 제한을 거는 것
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
     /*   List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
        for(String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;*/

        return roles.stream() // List<String>을 Stream<List<String>>으로 변환
                // SimpleGrantAuthority::new은 메소드 참조라고 호칭
//                .map(new Function<String, SimpleGrantedAuthority>() {
//                    @Override
//                   public SimpleGrantedAuthority apply(String str) {
//                       return new SimpleGrantedAuthority(str);
//                   }
//                })
                // .map(item -> new SimpleGrantedAuthority(item))와 같은 내용이다.
                .map(SimpleGrantedAuthority::new) // map은 똑같은 size의 스트림을 만든다. Stream<List<SimpleGrantedAuthority>>으로 변환
                .toList();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
