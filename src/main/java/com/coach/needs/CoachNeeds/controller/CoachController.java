package com.coach.needs.CoachNeeds.controller;


import com.coach.needs.CoachNeeds.Dto.ClientDto;
import com.coach.needs.CoachNeeds.Dto.ClientTypeDto;
import com.coach.needs.CoachNeeds.model.Coach;
import com.coach.needs.CoachNeeds.service.CoachService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coach.needs.CoachNeeds.utils.AuthenticationUtils.getAuthenticatedUserSubjectId;

@RestController
@RequestMapping("/coach")
public class CoachController {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CoachController.class);
    private final CoachService coachService;
    //private final KeycloakProvider kcProvider;


    public CoachController(CoachService kcAdminClient) {
        //this.kcProvider = kcProvider;
        this.coachService = kcAdminClient;
    }

//    @PostMapping(value = "/register")
//    public ResponseEntity<?> registerCoach(@RequestBody RegisterCoachDto user) {
//        Response createdResponse = coachService.createKeycloakUser(user);
//        return ResponseEntity.status(createdResponse.getStatus()).build();
//
//    }

//    @PostMapping("/login")
//    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginCoachDto loginRequest) {
//        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword()).build();
//        AccessTokenResponse accessTokenResponse = null;
//        try {
//            accessTokenResponse = keycloak.tokenManager().getAccessToken();
//            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);
//        } catch (BadRequestException ex) {
//            LOG.warn("invalid account. Coach probably hasn't verified email.", ex);
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
//        }
//
//    }

    @GetMapping
    public ResponseEntity<List<Coach>> getAllCoaches() {
        List<Coach> users = coachService.getAllCoaches();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @GetMapping("/{coach_id}")
    public ResponseEntity<Coach> getCoachById(@PathVariable("coach_id") String coachId) {
        Coach user = coachService.getCoachById(coachId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/add/client")
    public void addClient(@RequestBody ClientDto clientDto) {
        String coachId = getAuthenticatedUserSubjectId();
        coachService.addClient(clientDto, coachId);
    }

    @GetMapping("/{coach_id}/type/client")
    public ResponseEntity<ClientTypeDto> getClientType(@PathVariable("coach_id") String coachId) {
        ClientTypeDto clientTypeDto = coachService.getClientType(coachId);
        return ResponseEntity.status(HttpStatus.OK).body(clientTypeDto);
    }
}
