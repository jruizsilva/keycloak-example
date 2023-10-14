package keycloak.service.impl;

import keycloak.controller.dto.UserDTO;
import keycloak.service.IKeycloakService;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public class KeycloakServiceImpl implements IKeycloakService {
    @Override
    public List<UserRepresentation> findAllUsers() {
        return null;
    }

    @Override
    public List<UserRepresentation> searchUserByUsername(String username) {
        return null;
    }

    @Override
    public String createUser(UserDTO userDto) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public void updateUser(String userId,
                           UserDTO userDto) {

    }
}
