package mg.itu.ticketing.configuration;

import mg.matsd.javaframework.core.annotations.Configuration;
import mg.matsd.javaframework.core.annotations.ManagedInstance;
import mg.matsd.javaframework.security.base.AuthenticationManager;
import mg.matsd.javaframework.security.base.PasswordHasher;
import mg.matsd.javaframework.security.base.Security;
import mg.matsd.javaframework.security.base.implementation.SimpleSHA256PasswordHasher;
import mg.matsd.javaframework.security.provider.UserProvider;

@Configuration
public class ApplicationConfig {

    @ManagedInstance
    public PasswordHasher passwordHasher() {
        return new SimpleSHA256PasswordHasher();
    }

    @ManagedInstance
    public AuthenticationManager authenticationManager(UserProvider userService, PasswordHasher passwordHasher) {
        AuthenticationManager authenticationManager = new AuthenticationManager(userService, passwordHasher);
        authenticationManager.useDefaultStatefulStorageKey();

        return authenticationManager;
    }

    @ManagedInstance
    public Security security(AuthenticationManager authenticationManager) {
        return new Security().setAuthenticationManager(authenticationManager);
    }
}
