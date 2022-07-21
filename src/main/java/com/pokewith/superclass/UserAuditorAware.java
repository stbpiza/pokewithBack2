package com.pokewith.superclass;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        if (authentication == null || !authentication.isAuthenticated()) {
//            return null;
//        }
//        return Optional.ofNullable((String) authentication.getPrincipal());

        if (authentication == null){
            return Optional.ofNullable("null");
        }

        return Optional.ofNullable(authentication.getName());
    }
}
