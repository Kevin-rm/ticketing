package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import mg.itu.ticketing.entity.Plane;
import mg.matsd.javaframework.core.annotations.Component;

@Component
public class PlaneService {

    public Plane getById(final Integer id, final EntityManager entityManager) {
        Plane plane = entityManager.find(Plane.class, id);
        if (plane == null)
            throw new RuntimeException("Aucun avion trouvé avec l'identifiant: " + id);

        return plane;
    }
}
