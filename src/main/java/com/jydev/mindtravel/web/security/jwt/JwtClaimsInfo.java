package com.jydev.mindtravel.web.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@AllArgsConstructor
public class JwtClaimsInfo {
    private String email;
    private List<String> authorities;

    public JwtClaimsInfo(UsernamePasswordAuthenticationToken token) {
        List<String> grantAuthority = token.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        this.email = (String) token.getPrincipal();
        this.authorities = grantAuthority;
    }
}
