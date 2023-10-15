package keycloak.util;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;

public class KeycloakProvider {
    public static final String SERVER_URL = "http://localhost:9090";
    public static final String REALM_NAME = "spring-boot-realm-dev";
    public static final String REALM_MASTER = "master";
    public static final String ADMIN_CLI = "admin-cli";
    public static final String KEYCLOAK_CONSOLE_USERNAME = "admin";
    public static final String KEYCLOAK_CONSOLE_PASSWORD = "admin";
    public static final String CLIENT_SECRET = "GqV6Hb2dn5P65TzYNBhQIACdB5Rc9Mw9";

    public static RealmResource getRealmResource() {
        Keycloak keycloak =
                KeycloakBuilder.builder()
                               .serverUrl(SERVER_URL)
                               .realm(REALM_MASTER)
                               .clientId(ADMIN_CLI)
                               .username(KEYCLOAK_CONSOLE_USERNAME)
                               .password(KEYCLOAK_CONSOLE_PASSWORD)
                               .clientSecret(CLIENT_SECRET)
                               .resteasyClient(new ResteasyClientBuilderImpl()
                                                       .connectionPoolSize(10)
                                                       .build())
                               .build();
        return keycloak.realm(REALM_NAME);
    }

    public static UsersResource getUsersResource() {
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }
}
