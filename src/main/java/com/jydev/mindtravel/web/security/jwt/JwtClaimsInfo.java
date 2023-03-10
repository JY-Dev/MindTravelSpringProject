package com.jydev.mindtravel.web.security.jwt;

import lombok.*;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class JwtClaimsInfo {
    private String email;
    private List<String> authorities;
}
