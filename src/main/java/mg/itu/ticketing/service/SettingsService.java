package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import mg.itu.ticketing.entity.Settings;
import mg.itu.ticketing.entity.User;
import mg.itu.ticketing.request.SettingsRequest;
import mg.matsd.javaframework.core.annotations.Component;

@Component
public class SettingsService {

    public void save(
        final Settings settings,
        final SettingsRequest request,
        final EntityManager entityManager,
        final User user
    ) {
        settings.setMinReservationHours(request.getMinReservationHours());
        settings.setMinCancellationHours(request.getMinCancellationHours());
        settings.setModifiedBy(user);

        if (settings.getId() == null) entityManager.persist(settings);
        else entityManager.merge(settings);
    }
}
