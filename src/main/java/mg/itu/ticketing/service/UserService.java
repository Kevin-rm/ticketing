package mg.itu.ticketing.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import mg.itu.ticketing.request.RegistrationRequest;
import mg.itu.ticketing.utils.DatabaseUtils;
import mg.itu.ticketing.utils.Facade;
import mg.matsd.javaframework.core.utils.Assert;
import mg.matsd.javaframework.core.utils.CollectionUtils;
import mg.matsd.javaframework.core.utils.StringUtils;
import mg.matsd.javaframework.di.annotations.Component;
import mg.matsd.javaframework.security.base.User;
import mg.matsd.javaframework.security.base.UserRole;
import mg.matsd.javaframework.security.exceptions.DuplicateUserException;
import mg.matsd.javaframework.security.exceptions.UserNotFoundException;
import mg.matsd.javaframework.security.provider.UserProvider;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserService implements UserProvider {
    private final RoleService roleService;

    public void insert(final RegistrationRequest request) {
        mg.itu.ticketing.entity.User user = new mg.itu.ticketing.entity.User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(Facade.passwordHasher().hash(request.getPassword()));

        addUser(user);
    }

    @Override
    public void addUser(User user) throws DuplicateUserException {
        Assert.state(user instanceof mg.itu.ticketing.entity.User,
            "L'argument user doit être une instance de \"mg.itu.ticketing.entity.User\"");

        DatabaseUtils.executeTransactional(entityManager -> {
            List<? extends UserRole> roles = user.getRoles();
            if (CollectionUtils.isEmpty(roles)) ((mg.itu.ticketing.entity.User) user).setUserRoles(List.of(
                roleService.getOrCreateDefault(entityManager)
            ));

            entityManager.persist(user);
        });
    }

    @Override
    public void removeUser(User user) throws UserNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public User loadUserByIdentifier(String s) throws UserNotFoundException {
        Assert.state(StringUtils.hasText(s));

        try {
            return DatabaseUtils.execute(entityManager ->
                entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", mg.itu.ticketing.entity.User.class)
                    .setParameter("email", s)
                    .getSingleResult());
        } catch (NoResultException e) {
            throw new UserNotFoundException(s);
        }
    }
}
