package com.pokewith.auth;

import jakarta.servlet.http.HttpServletRequest;

public interface UsernameService {

    Long getUsername (HttpServletRequest request);

    Long getUsernameOrNull(HttpServletRequest request);
}
