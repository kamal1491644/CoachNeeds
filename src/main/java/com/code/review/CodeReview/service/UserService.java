package com.code.review.CodeReview.service;

import com.code.review.CodeReview.Dto.CodeRequestDto;
import com.code.review.CodeReview.config.KeycloakProvider;
import com.code.review.CodeReview.Dto.CreateUserRequestDto;
import com.code.review.CodeReview.model.Code;
import com.code.review.CodeReview.model.User;
import com.code.review.CodeReview.repository.CodeRepository;
import com.code.review.CodeReview.repository.UserRepository;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.*;


@Service
public class UserService {
    private final KeycloakProvider kcProvider;
    @Value("${keycloak.realm}")
    public String realm;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CodeRepository codeRepository;


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
        kcUser.setUsername(user.getUsername());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getFirstname());
        kcUser.setLastName(user.getLastname());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);
        Response response = usersResource.create(kcUser);
        kcProvider.addRealmRoleToUser(user.getUsername(), user.getRole());
        if (response.getStatus() == 201) {
            User localUser = new User();
            saveUser(localUser, kcUser, user);
            //TODO: Handle failing of saving user in local database
        }
        return response;
    }

    public void saveUser(User user, UserRepresentation kcUser, CreateUserRequestDto inUser) {
        user.setFirstName(kcUser.getFirstName());
        user.setLastName(kcUser.getLastName());
        user.setEmail(kcUser.getEmail());
        user.setCreatedAt(Instant.now());
        user.setUserName(kcUser.getUsername());
        user.setRole(inUser.getRole());
        user.setSubjectId(kcProvider.getUserSubjectId(user.getUserName()));
        userRepository.save(user);
    }

    public void submitCode(CodeRequestDto codeRequestDto, String subjectId) {
        User user = userRepository.findBySubjectId(subjectId);
        Code code = new Code();
        code.setCode(codeRequestDto.getCode());
        code.setProgrammingLanguages(codeRequestDto.getProgrammingLanguages());
        code.setCreatedAt(Instant.now());
        code.setUser(user);
        code.setComment(codeRequestDto.getComment());
        code.setRanking(codeRequestDto.getRanking());
        codeRepository.save(code);
    }


}
