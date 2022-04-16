package com.code.review.CodeReview.service;

import com.code.review.CodeReview.Dto.CreateUserRequestDto;
import com.code.review.CodeReview.config.KeycloakProvider;
import com.code.review.CodeReview.model.User;
import com.code.review.CodeReview.repository.UserRepository;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Collections;
import java.util.List;


@Service
public class UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final KeycloakProvider kcProvider;
    @Value("${keycloak.realm}")
    public String realm;

    @Autowired
    UserRepository userRepository;

    public UserService(KeycloakProvider keycloakProvider) {
        this.kcProvider = keycloakProvider;
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        logger.info("createPasswordCredentials() --> creating credentials.....");
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        logger.info("createPasswordCredentials() --> credentials created.....");
        return passwordCredentials;
    }

    public Response createKeycloakUser(CreateUserRequestDto user) {
        logger.info("createKeycloakUser() --> creating user: {}", user);
        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getUserName());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);
        Response response = usersResource.create(kcUser);
        //kcProvider.addRealmRoleToUser(user.getUserName(), user.getRole());
        if (response.getStatus() == 201) {
            logger.debug("createKeycloakUser() --> User created :{}", kcUser);
            logger.debug("createKeycloakUser() --> Saving user in local storage....");
            User localUser = new User();
            saveUser(localUser, kcUser, user);

            //TODO: Handle failing of saving user in local database
        }
        return response;
    }

    public void saveUser(User user, UserRepresentation kcUser, CreateUserRequestDto inUser) {
        logger.debug("saveUser() --> saving user in local storage :{}", inUser);
        user.setFirstName(kcUser.getFirstName());
        user.setLastName(kcUser.getLastName());
        user.setEmail(kcUser.getEmail());
        user.setCreatedAt(Instant.now());
        user.setUserName(kcUser.getUsername());
        user.setSubjectId(kcProvider.getUserSubjectId(user.getUserName()));
        user.setAddress(inUser.getAddress());
        user.setGender(inUser.getGender());
        user.setDateOfBirth(inUser.getDateOfBirth());
        user.setPassword(inUser.getPassword());
        userRepository.save(user);
        logger.debug("saveUser() --> User created in local storage :{}", user);
    }

    public List<User> getAllUsers() {
        logger.info("getAllUsers() --> getting all users.....");
        List<User> users = userRepository.findAll();
        logger.debug("getAllUsers() --> listed users:{}", users);
        return users;
    }

    public User getUserById(String userId) {
        logger.info("getUserById() --> getting user by id:{}", userId);
        User user = userRepository.findBySubjectId(userId);
        logger.debug("getUserById() --> user :{} with id :{}",user, userId);
        return user;
    }
}
