package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import mg.itu.ticketing.entity.Plane;
import mg.matsd.javaframework.di.annotations.Component;

import java.util.List;

@Component
public class PlaneService {

    public List<Plane> getAll(final EntityManager entityManager) {
        return entityManager.createQuery("SELECT p FROM Plane p", Plane.class).getResultList();
    }

    public Plane getById(final Integer id, final EntityManager entityManager) {
        Plane plane = entityManager.find(Plane.class, id);
        if (plane == null)
            throw new RuntimeException("Aucun avion trouvé avec l'identifiant: " + id);

        return plane;
    }
}
