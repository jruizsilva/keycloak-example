package keycloak.service;

import keycloak.controller.dto.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface IKeycloakService {
    List<UserRepresentation> findAllUsers();
    List<UserRepresentation> searchUserByUsername(String username);
    String createUser(UserDTO userDto);
    void deleteUser(String userId);
    void updateUser(String userId,
                    UserDTO userDto);
}
