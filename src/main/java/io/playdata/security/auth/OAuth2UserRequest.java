package io.playdata.security.auth;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

public class OAuth2UserRequest {
    private final ClientRegistration clientRegistration;
    private final OAuth2AccessToken accessToken;
}


