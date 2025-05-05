package com.lievasoft.cefac.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lievasoft.cefac.entity.Permission.PRODUCT_READ;
import static com.lievasoft.cefac.entity.Permission.PRODUCT_WRITE;

@Getter
public enum Role {
    ADMIN(Set.of(PRODUCT_WRITE, PRODUCT_READ)),
    ASSISTANT(Set.of(PRODUCT_READ)),
    COUNTER(Set.of(PRODUCT_READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
