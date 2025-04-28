package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import mg.itu.ticketing.entity.Settings;
import mg.itu.ticketing.entity.User;
import mg.itu.ticketing.request.SettingsRequest;
import mg.matsd.javaframework.core.annotations.Component;

@Component
public class SettingsService {

    public Settings getFirstOrNew(final EntityManager entityManager) {
        try {
            return entityManager.createQuery("SELECT s FROM Settings s ORDER BY s.id", Settings.class)
                .setMaxResults(1)
                .getSingleResult();
        } catch (NoResultException e) { return new Settings(); }
    }

    public void save(
        final SettingsRequest request, final EntityManager entityManager, final User user
    ) {
        final Settings settings = getFirstOrNew(entityManager);

        settings.setMinReservationHours(request.getMinReservationHours());
        settings.setMinCancellationHours(request.getMinCancellationHours());
        settings.setModifiedBy(user);

        if (settings.getId() == null) entityManager.persist(settings);
        else entityManager.merge(settings);
    }
}
