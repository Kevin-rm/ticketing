package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import mg.itu.ticketing.entity.Seat;
import mg.matsd.javaframework.core.annotations.Component;

@Component
public class SeatService {

    public Seat getById(final Integer id, final EntityManager entityManager) {
        Seat seat = entityManager.find(Seat.class, id);
        if (seat == null)
            throw new RuntimeException("Aucun siège trouvé avec l'identifiant: " + id);

        return seat;
    }
}
