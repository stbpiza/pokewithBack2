package com.pokewith.auth;

import javax.servlet.http.HttpServletRequest;

public interface UsernameService {

    Long getUsername (HttpServletRequest request);

    Long getUsernameOrNull(HttpServletRequest request);
}
