package com.code.review.CodeReview.service;

import com.code.review.CodeReview.config.KeycloakProvider;
import com.code.review.CodeReview.Dto.CreateUserRequestDto;
import com.code.review.CodeReview.model.User;
import com.code.review.CodeReview.repository.UserRepository;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Collections;


@Service
public class UserService {
    private final KeycloakProvider kcProvider;
    @Value("${keycloak.realm}")
    public String realm;

    @Autowired
    UserRepository userRepository;


    public UserService(KeycloakProvider keycloakProvider) {
        this.kcProvider = keycloakProvider;
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    public Response createKeycloakUser(CreateUserRequestDto user) {
        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getEmail());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getFirstname());
        kcUser.setLastName(user.getLastname());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);

        Response response = usersResource.create(kcUser);

        if (response.getStatus() == 201) {
            User localUser = new User();
            saveUser(localUser, kcUser);
            //TODO: Handle failing of saving user in local database
        }
        return response;
    }

    public void saveUser(User user, UserRepresentation kcUser){
        user.setFirstName(kcUser.getFirstName());
        user.setLastName(kcUser.getLastName());
        user.setEmail(kcUser.getEmail());
        user.setCreatedAt(Instant.now());
        user.setUserName(kcUser.getUsername());
        userRepository.save(user);
    }
}
