package mg.itu.ticketing.utils;

import mg.itu.prom16.utils.AuthFacade;
import mg.itu.prom16.utils.WebFacade;
import mg.itu.ticketing.entity.User;
import mg.matsd.javaframework.security.base.PasswordHasher;

public class Facade extends WebFacade {

    private Facade() { }

    public static PasswordHasher passwordHasher() {
        return webApplicationContainer().getManagedInstance(PasswordHasher.class);
    }

    public static User requireCurrentUser() {
        return AuthFacade.requireCurrentUser(User.class);
    }
}
