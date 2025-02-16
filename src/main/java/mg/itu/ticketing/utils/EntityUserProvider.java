package mg.itu.ticketing.utils;

import mg.matsd.javaframework.core.annotations.Component;
import mg.matsd.javaframework.core.utils.Assert;
import mg.matsd.javaframework.core.utils.StringUtils;
import mg.matsd.javaframework.security.base.User;
import mg.matsd.javaframework.security.exceptions.DuplicateUserException;
import mg.matsd.javaframework.security.exceptions.UserNotFoundException;
import mg.matsd.javaframework.security.provider.UserProvider;

@Component
public class EntityUserProvider implements UserProvider {

    @Override
    public void addUser(User user) throws DuplicateUserException {
        Assert.state(user instanceof mg.itu.ticketing.entity.User,
            "L'argument user doit être une instance de \"mg.itu.ticketing.entity.User\"");

        DatabaseUtils.executeWithTransaction(entityManager -> entityManager.persist(user));
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
