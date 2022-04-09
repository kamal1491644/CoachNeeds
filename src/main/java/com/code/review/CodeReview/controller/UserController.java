package com.code.review.CodeReview.controller;


import com.code.review.CodeReview.Dto.CodeRequestDto;
import com.code.review.CodeReview.config.KeycloakProvider;
import com.code.review.CodeReview.Dto.CreateUserRequestDto;
import com.code.review.CodeReview.Dto.LoginRequestDto;
import com.code.review.CodeReview.model.User;
import com.code.review.CodeReview.repository.UserRepository;
import com.code.review.CodeReview.service.UserService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(UserController.class);
    private final UserService kcAdminClient;
    private final KeycloakProvider kcProvider;


    public UserController(UserService kcAdminClient, KeycloakProvider kcProvider) {
        this.kcProvider = kcProvider;
        this.kcAdminClient = kcAdminClient;
    }


    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDto user) {
        Response createdResponse = kcAdminClient.createKeycloakUser(user);
        return ResponseEntity.status(createdResponse.getStatus()).build();

    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@NotNull @RequestBody LoginRequestDto loginRequest) {
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
    @PostMapping("/{subject_id}/code")

    public void submitCode(@RequestBody CodeRequestDto codeRequestDto,
                           @PathVariable("subject_id") String subjectId){
        kcAdminClient.submitCode(codeRequestDto,subjectId);
    }




}
