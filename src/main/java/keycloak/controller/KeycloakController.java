package keycloak.controller;

import keycloak.service.IKeycloakService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/keycloak/users")
@PreAuthorize("hasRole('admin_client_role')")
@RequiredArgsConstructor
public class KeycloakController {
    private IKeycloakService keycloakService;

    @GetMapping
    public ResponseEntity<List<UserRepresentation>> findAllUsers() {
        return ResponseEntity.ok(keycloakService.findAllUsers());
    }

    @GetMapping("search/{username}")
    public ResponseEntity<List<UserRepresentation>> searchUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(keycloakService.searchUserByUsername(username));
    }
}
