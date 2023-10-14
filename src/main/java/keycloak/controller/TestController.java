package keycloak.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/helloAdmin")
    @PreAuthorize("hasRole('admin_client_role')") // only ADMIN can access this endpoint (role)
    public String helloAdmin() {
        return "Hello Admin with Keycloak - ADMIN";
    }

    @GetMapping("/helloUser")
    @PreAuthorize("hasRole('user_client_role') or hasRole('admin_client_role')")
    // only USER or ADMIN can access this endpoint (role)
    public String helloUser() {
        return "Hello User with Keycloak - USER";
    }
}
