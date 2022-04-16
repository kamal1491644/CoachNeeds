package com.code.review.CodeReview.config;


import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Getter
public class KeycloakProvider {

    private static Keycloak keycloak = null;
    @Value("${keycloak.auth-server-url}")
    public String serverURL;
    @Value("${keycloak.realm}")
    public String realm;
    @Value("${keycloak.resource}")
    public String clientID;
    @Value("${keycloak.credentials.secret}")
    public String clientSecret;

    public KeycloakProvider() {
    }

    public Keycloak getInstance() {
        if (keycloak == null) {

            return KeycloakBuilder.builder().realm(realm).serverUrl(serverURL).clientId(clientID).clientSecret(clientSecret).grantType(OAuth2Constants.CLIENT_CREDENTIALS).build();
        }
        return keycloak;
    }


    public KeycloakBuilder newKeycloakBuilderWithPasswordCredentials(String username, String password) {
        return KeycloakBuilder.builder() //
                .realm(realm) //
                .serverUrl(serverURL)//
                .clientId(clientID) //
                .clientSecret(clientSecret) //
                .username(username) //
                .password(password);
    }

    public List<String> getAllRoles() {
        ClientRepresentation clientRep = keycloak.realm(realm).clients().findByClientId(clientID).get(0);
        List<String> availableRoles = keycloak.realm(realm).clients().get(clientRep.getId()).roles().list().stream().map(role -> role.getName()).collect(Collectors.toList());
        return availableRoles;
    }

    public void addRealmRoleToUser(String userName, String role_name) {
        keycloak = getInstance();
        String client_id = keycloak.realm(realm).clients().findByClientId(clientID).get(0).getId();
        String userId = keycloak.realm(realm).users().search(userName).get(0).getId();
        UserResource user = keycloak.realm(realm).users().get(userId);
        List<RoleRepresentation> roleToAdd = new LinkedList<>();
        roleToAdd.add(keycloak.realm(realm).clients().get(client_id).roles().get("user").toRepresentation());
        roleToAdd.add(keycloak.realm(realm).clients().get(client_id).roles().get(role_name).toRepresentation());
        user.roles().clientLevel(client_id).add(roleToAdd);
    }

    public String getUserSubjectId(String userName) {
        keycloak = getInstance();
        String userId = keycloak.realm(realm).users().search(userName).get(0).getId();
        return userId;
    }

    public JsonNode refreshToken(String refreshToken) throws UnirestException {
        String url = serverURL + "/realms/" + realm + "/protocol/openid-connect/token";
        return Unirest.post(url).header("Content-Type", "application/x-www-form-urlencoded").field("client_id", clientID).field("client_secret", clientSecret).field("refresh_token", refreshToken).field("grant_type", "refresh_token").asJson().getBody();
    }
}