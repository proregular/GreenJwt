package com.green.jwt.config.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

class MyFunction implements Function<String, SimpleGrantedAuthority> {
    @Override
    public SimpleGrantedAuthority apply(String str) {
        return new SimpleGrantedAuthority(str);
    }
}
//동일성 - 주소값이 같느냐?
//동등성 - 주소값은 다른 존재끼리 같은 값을 가지고 있음


@Getter
@EqualsAndHashCode // Equals, HashCode 메소드 오버라이딩 > Test 용도
@RequiredArgsConstructor
public class JwtUser implements UserDetails {
    private final long signedUserId;
    private final List<UserRole> roles; //인가(권한)처리 때 사용, ROLE_이름, ROLE_USER, ROLE_ADMIN

    //리턴타입으로 콜렉션인데 콜렉션에 방 하나하나의 타입은 <> 지정을 한다.
    //<?>이렇게 하면 Object가 되기 때문에 모든 타입이 허용이 된다.
    //<? extends GrantedAuthority>는 지정 타입이 꼭 GrantedAuthority포함, GrantedAuthority를 상속받은 객체만 가능하도록 제한을 거는 것

    //<? super GrantedAuthority>는 지정 타입이 꼭  GrantedAuthority포함, GrantedAuthority의 부모 객체만 가능하도록 제한을 거는 것

    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
//        for(String role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role));
//        }
//        return authorities;

        Function fn2 = new MyFunction();

        Function fn = new Function<String, SimpleGrantedAuthority>() {
            @Override
            public SimpleGrantedAuthority apply(String str) {
                return new SimpleGrantedAuthority(str);
            }
        };


        return roles.stream() //List<String>을 Stream<List<String>>으로 변환
                // SimpleGrantedAuthority::new은 메소드 참조라고 호칭
                //.map(fn)
                // .map(str -> new SimpleGrantedAuthority(str))와 같은 내용이다.
                // .map(item -> { return new SimpleGrantedAuthority(item); }) //와 같은 내용이다.
                //.map(fn2)
                .map(item -> new SimpleGrantedAuthority(String.format("ROLE_%s", item.name()))) //map은 똑같은 size의 스트림을 만든다. Stream<List<SimpleGrantedAuthority>>으로 변환,
                .toList();

        //return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }



    @JsonIgnore
    @Override
    public String getPassword() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return "";
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
