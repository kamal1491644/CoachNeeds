package com.code.review.CodeReview.controller;


import com.code.review.CodeReview.Dto.RegisterUserDto;
import com.code.review.CodeReview.Dto.LoginUserDto;
import com.code.review.CodeReview.config.KeycloakProvider;
import com.code.review.CodeReview.service.UserService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final KeycloakProvider kcProvider;


    public UserController(UserService kcAdminClient, KeycloakProvider kcProvider) {
        this.kcProvider = kcProvider;
        this.userService = kcAdminClient;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto user) {
        LOG.info("registerUser() | register user :{} ", user);
        Response createdResponse = userService.createUser(user);
        LOG.debug("registerUser() | Saved and registered user: {}", user);
        return ResponseEntity.status(createdResponse.getStatus()).build();

    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginUserDto loginRequest) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword()).build();
        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);
        } catch (BadRequestException ex) {
            LOG.warn("invalid account. User probably hasn't verified email.", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
        }

    }


}
