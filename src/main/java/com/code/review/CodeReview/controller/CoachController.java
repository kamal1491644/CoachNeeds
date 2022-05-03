package com.code.review.CodeReview.controller;


import com.code.review.CodeReview.Dto.RegisterCoachDto;
import com.code.review.CodeReview.Dto.LoginCoachDto;
import com.code.review.CodeReview.config.KeycloakProvider;
import com.code.review.CodeReview.model.Coach;
import com.code.review.CodeReview.service.CoachService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/coach")
public class CoachController {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CoachController.class);
    private final CoachService userService;
    private final KeycloakProvider kcProvider;


    public CoachController(CoachService kcAdminClient, KeycloakProvider kcProvider) {
        this.kcProvider = kcProvider;
        this.userService = kcAdminClient;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerCoach(@RequestBody RegisterCoachDto user) {
        Response createdResponse = userService.createKeycloakUser(user);
        return ResponseEntity.status(createdResponse.getStatus()).build();

    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginCoachDto loginRequest) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword()).build();
        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);
        } catch (BadRequestException ex) {
            LOG.warn("invalid account. Coach probably hasn't verified email.", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
        }

    }

    @GetMapping
    public ResponseEntity<List<Coach>> getAllCoaches(){
        List<Coach> users = userService.getAllCoaches();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{coach_id}")
    public ResponseEntity<Coach> getCoachById(@PathVariable("coach_id") String userId){
        Coach user = userService.getCoachById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
