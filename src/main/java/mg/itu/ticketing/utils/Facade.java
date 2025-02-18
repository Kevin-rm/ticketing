package mg.itu.ticketing.utils;

import mg.itu.prom16.utils.WebFacade;
import mg.matsd.javaframework.security.base.PasswordHasher;

public class Facade extends WebFacade {

    public static PasswordHasher passwordHasher() {
        return getWebApplicationContainer().getManagedInstance(PasswordHasher.class);
    }
}
