package com.coach.needs.CoachNeeds.service;

import com.coach.needs.CoachNeeds.Dto.RegisterCoachDto;
import com.coach.needs.CoachNeeds.model.Client;
import com.coach.needs.CoachNeeds.Dto.ClientDto;
import com.coach.needs.CoachNeeds.Dto.ClientTypeDto;
import com.coach.needs.CoachNeeds.model.Coach;
import com.coach.needs.CoachNeeds.repository.ClientRepository;
import com.coach.needs.CoachNeeds.repository.CoachRepository;
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
public class CoachService {
    private final static Logger logger = LoggerFactory.getLogger(CoachService.class);
    //private final KeycloakProvider kcProvider;
    @Value("${keycloak.realm}")
    public String realm;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private ClientRepository clientRepository;

//    public CoachService(KeycloakProvider keycloakProvider) {
//        this.kcProvider = keycloakProvider;
//    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        logger.info("createPasswordCredentials() --> creating credentials.....");
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        logger.info("createPasswordCredentials() --> credentials created.....");
        return passwordCredentials;
    }

//    public Response createKeycloakUser(RegisterCoachDto user) {
//        logger.info("createKeycloakUser() --> creating user: {}", user);
//        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();
//        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
//        UserRepresentation kcUser = new UserRepresentation();
//        kcUser.setUsername(user.getUserName());
//        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
//        kcUser.setFirstName(user.getFirstName());
//        kcUser.setLastName(user.getLastName());
//        kcUser.setEmail(user.getEmail());
//        kcUser.setEnabled(true);
//        kcUser.setEmailVerified(true);
//        Response response = usersResource.create(kcUser);
//        //kcProvider.addRealmRoleToUser(user.getUserName(), user.getRole());
//        if (response.getStatus() == 201) {
//            logger.debug("createKeycloakUser() --> Coach created :{}", kcUser);
//            logger.debug("createKeycloakUser() --> Saving user in local storage....");
//            Coach localUser = new Coach();
//            saveCoach(localUser, kcUser, user);
//
//            //TODO: Handle failing of saving user in local database
//        }
//        return response;
//    }

//    public void saveCoach(Coach user, UserRepresentation kcUser, RegisterCoachDto inUser) {
//        logger.debug("saveUser() --> saving user in local storage :{}", inUser);
//        user.setFirstName(kcUser.getFirstName());
//        user.setLastName(kcUser.getLastName());
//        user.setEmail(kcUser.getEmail());
//        user.setCreatedAt(Instant.now());
//        user.setUserName(kcUser.getUsername());
//        user.setSubjectId(kcProvider.getCoachSubjectId(user.getUserName()));
//        user.setAddress(inUser.getAddress());
//        user.setGender(inUser.getGender());
//        user.setDateOfBirth(inUser.getDateOfBirth());
//        user.setPassword(inUser.getPassword());
//        coachRepository.save(user);
//        logger.debug("saveUser() --> Coach created in local storage :{}", user);
//    }

    public List<Coach> getAllCoaches() {
        logger.info("getAllUsers() --> getting all users.....");
        List<Coach> users = coachRepository.findAll();
        logger.debug("getAllUsers() --> listed users:{}", users);
        return users;
    }

    public Coach getCoachById(String coachId) {
        logger.info("getUserById() --> getting user by id:{}", coachId);
        Coach user = coachRepository.findBySubjectId(coachId);
        logger.debug("getUserById() --> user :{} with id :{}",user, coachId);
        return user;
    }

    public void addClient(ClientDto clientDto, String coachId){
        Client client = new Client();
        Coach coach = getCoachById(coachId);
        client.setCoach(coach);
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setAddress(clientDto.getAddress());
        client.setCreatedAt(Instant.now());
        client.setGender(clientDto.getGender());
        client.setEmail(clientDto.getEmail());
        client.setDateOfBirth(clientDto.getDateOfBirth());
        client.setHeight(clientDto.getHeight());
        client.setWeight(clientDto.getWeight());
        client.setAge(clientDto.getAge());
        client.setClientState(clientDto.getState());
        client.setPaidMoney(clientDto.getPaidMoney());
        client.setMobile(clientDto.getMobile());
        client.setWhatsApp(clientDto.getWhatsApp());
        client.setTrainingType(clientDto.getTrainingType());
        client.setCountry(clientDto.getCountry());
        clientRepository.save(client);
    }

    public ClientTypeDto getClientType(String coachId){
       List<Client> natural = clientRepository.findByTrainingType("natural",Integer.valueOf(coachId));
        List<Client> steroids = clientRepository.findByTrainingType("steroids",Integer.valueOf(coachId));
        ClientTypeDto clientTypeDto = new ClientTypeDto();
        clientTypeDto.setNatural(natural.size());
        clientTypeDto.setSteroids(steroids.size());
        return clientTypeDto;
    }
}
