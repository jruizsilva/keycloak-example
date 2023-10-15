package keycloak.service.impl;

import jakarta.ws.rs.core.Response;
import keycloak.controller.dto.UserDTO;
import keycloak.service.IKeycloakService;
import keycloak.util.KeycloakProvider;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class KeycloakServiceImpl implements IKeycloakService {
    @Override
    public List<UserRepresentation> findAllUsers() {
        return KeycloakProvider.getUsersResource()
                               .list();
    }

    @Override
    public List<UserRepresentation> searchUserByUsername(String username) {
        return KeycloakProvider.getUsersResource()
                               .searchByUsername(username,
                                                 true);
    }

    @Override
    public String createUser(@NonNull UserDTO userDTO) {
        int status = 0;

        UsersResource usersResource = KeycloakProvider.getUsersResource();
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userDTO.getFirstName());
        userRepresentation.setLastName(userDTO.getLastName());
        userRepresentation.setEmail(userDTO.getEmail());
        userRepresentation.setUsername(userDTO.getUsername());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);

        Response response = usersResource.create(userRepresentation);
        status = response.getStatus();
        if (status == 201) {
            String path = response.getLocation()
                                  .getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(OAuth2Constants.PASSWORD);
            credentialRepresentation.setValue(userDTO.getPassword());

            usersResource.get(userId)
                         .resetPassword(credentialRepresentation);

            RealmResource realmResource = KeycloakProvider.getRealmResource();
            List<RoleRepresentation> roleRepresentations = null;
            if (userDTO.getRoles() == null || userDTO.getRoles()
                                                     .isEmpty()) {
                roleRepresentations = List.of(realmResource.roles()
                                                           .get("user")
                                                           .toRepresentation());
            } else {
                roleRepresentations =
                        realmResource.roles()
                                     .list()
                                     .stream()
                                     .filter(role -> userDTO.getRoles()
                                                            .stream()
                                                            .anyMatch(roleName -> roleName.equalsIgnoreCase(role.getName())))
                                     .toList();
            }
            realmResource.users()
                         .get(userId)
                         .roles()
                         .realmLevel()
                         .add(roleRepresentations);
            return "User created successfully";
        } else if (status == 409) {
            log.error("User already exists: {}",
                      userDTO.getUsername());
            return "User already exists";
        } else {
            log.error("User creation failed");
            return "User creation failed";
        }
    }

    @Override
    public void deleteUser(String userId) {
        KeycloakProvider.getUsersResource()
                        .get(userId)
                        .remove();
    }

    @Override
    public void updateUser(String userId,
                           @NonNull UserDTO userDTO) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(OAuth2Constants.PASSWORD);
        credentialRepresentation.setValue(userDTO.getPassword());

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userDTO.getFirstName());
        userRepresentation.setLastName(userDTO.getLastName());
        userRepresentation.setEmail(userDTO.getEmail());
        userRepresentation.setUsername(userDTO.getUsername());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        UserResource userResource = KeycloakProvider.getUsersResource()
                                                    .get(userId);
        userResource.update(userRepresentation);
    }
}
