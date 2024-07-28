package com.gt.genti.user.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

public class UserPrincipal implements UserDetails {

    private final User user;
    private final Collection<GrantedAuthority> grantedAuthorities;
    public UserPrincipal(User user) {
        this.user = user;
        this.grantedAuthorities = user.getId() == null ?
                                List.of(new SimpleGrantedAuthority("ANONYMOUS")) :
                                createAuthorities(user.getUserRole().getRoles());
    }

    private Collection<GrantedAuthority> createAuthorities(String roles) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : roles.split(",")) {
            if (!StringUtils.hasText(role))
                continue;
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    public UserRole getUserRole(){return user.getUserRole();}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActivate();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return user.isActivate();
    }

    public Long getUserId() {
        return user.getId();
    }

}
