package mg.itu.ticketing.configuration;

import mg.matsd.javaframework.core.annotations.Configuration;
import mg.matsd.javaframework.core.annotations.ManagedInstance;
import mg.matsd.javaframework.core.annotations.Nullable;
import mg.matsd.javaframework.core.io.ClassPathResource;
import mg.matsd.javaframework.core.io.Resource;
import mg.matsd.javaframework.security.base.AuthenticationManager;
import mg.matsd.javaframework.security.base.PasswordHasher;
import mg.matsd.javaframework.security.base.Security;
import mg.matsd.javaframework.security.base.implementation.SimpleSHA256PasswordHasher;
import mg.matsd.javaframework.security.provider.UserProvider;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class ApplicationConfig {
    private static final String APP_PROPERTIES_FILENAME = "app.properties";
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();

        try (Resource resource = new ClassPathResource(APP_PROPERTIES_FILENAME)) {
            PROPERTIES.load(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public static Object getProperty(final String key, @Nullable final Object defaultValue) {
        return PROPERTIES.getOrDefault(key, defaultValue);
    }

    @Nullable
    public static Object getProperty(final String key) {
        return getProperty(key, null);
    }

    public static String apiBaseurl() {
        return (String) getProperty("api.baseurl");
    }

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
