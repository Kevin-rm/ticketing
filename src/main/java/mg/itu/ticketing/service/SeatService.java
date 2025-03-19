package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import mg.itu.ticketing.entity.Plane;
import mg.itu.ticketing.entity.Seat;
import mg.matsd.javaframework.core.annotations.Component;

import java.util.List;

@Component
public class SeatService {

    public List<Seat> getAllByPlane(final Plane plane, final EntityManager entityManager) {
        return entityManager.createQuery("SELECT s FROM Seat s WHERE s.plane = :plane", Seat.class)
            .setParameter("plane", plane)
            .getResultList();
    }

    public Seat getById(final Integer id, final EntityManager entityManager) {
        Seat seat = entityManager.find(Seat.class, id);
        if (seat == null)
            throw new RuntimeException("Aucun siège trouvé avec l'identifiant: " + id);

        return seat;
    }
}
