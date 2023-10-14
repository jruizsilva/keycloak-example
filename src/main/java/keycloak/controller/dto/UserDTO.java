package keycloak.controller.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Set;

@Value
@RequiredArgsConstructor
@Builder
public class UserDTO {
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    Set<String> roles;
}
