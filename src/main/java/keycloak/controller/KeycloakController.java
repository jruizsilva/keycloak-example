package keycloak.controller;

import keycloak.controller.dto.UserDTO;
import keycloak.service.IKeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/keycloak/users")
@PreAuthorize("hasRole('admin_client_role')")
@RequiredArgsConstructor
public class KeycloakController {
    private final IKeycloakService keycloakService;

    @GetMapping
    public ResponseEntity<List<UserRepresentation>> findAllUsers() {
        return ResponseEntity.ok(keycloakService.findAllUsers());
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<UserRepresentation>> searchUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(keycloakService.searchUserByUsername(username));
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        String response = keycloakService.createUser(userDTO);
        return ResponseEntity.created(new URI("/keycloak/users"))
                             .body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId,
                                             @RequestBody UserDTO userDTO) throws URISyntaxException {
        keycloakService.updateUser(userId,
                                   userDTO);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        keycloakService.deleteUser(userId);
        return ResponseEntity.noContent()
                             .build();
    }
}
