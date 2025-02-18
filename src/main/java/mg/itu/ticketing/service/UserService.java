package mg.itu.ticketing.service;

import mg.itu.ticketing.request.RegistrationRequest;
import mg.itu.ticketing.utils.DatabaseUtils;
import mg.itu.ticketing.utils.Facade;
import mg.matsd.javaframework.core.annotations.Component;
import mg.matsd.javaframework.core.utils.Assert;
import mg.matsd.javaframework.core.utils.StringUtils;
import mg.matsd.javaframework.security.base.User;
import mg.matsd.javaframework.security.exceptions.DuplicateUserException;
import mg.matsd.javaframework.security.exceptions.UserNotFoundException;
import mg.matsd.javaframework.security.provider.UserProvider;

@Component
public class UserService implements UserProvider {

    public void save(final RegistrationRequest registrationRequest) {
        mg.itu.ticketing.entity.User user = new mg.itu.ticketing.entity.User();
        user.setFirstname(registrationRequest.getFirstname());
        user.setLastname(registrationRequest.getLastname());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(Facade.passwordHasher()
            .hash(registrationRequest.getPassword())
        );

        addUser(user);
    }

    @Override
    public void addUser(User user) throws DuplicateUserException {
        Assert.state(user instanceof mg.itu.ticketing.entity.User,
            "L'argument user doit être une instance de \"mg.itu.ticketing.entity.User\"");

        DatabaseUtils.executeTransactional(entityManager -> entityManager.persist(user));
    }

    @Override
    public void removeUser(User user) throws UserNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public User loadUserByIdentifier(String s) throws UserNotFoundException {
        Assert.state(StringUtils.hasText(s));

        User user = DatabaseUtils.execute(entityManager ->
            entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", mg.itu.ticketing.entity.User.class)
                .setParameter("email", s)
                .getSingleResult());

        if (user == null) throw new UserNotFoundException(s);
        return user;
    }
}
