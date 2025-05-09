package mg.itu.ticketing.service;

import jakarta.persistence.EntityManager;
import mg.itu.ticketing.entity.City;
import mg.matsd.javaframework.di.annotations.Component;

import java.util.List;

@Component
public class CityService {

    public List<City> getAll(final EntityManager entityManager) {
        return entityManager.createQuery("SELECT c FROM City c", City.class).getResultList();
    }

    public City getById(final Integer id, final EntityManager entityManager) {
        City city = entityManager.find(City.class, id);
        if (city == null)
            throw new RuntimeException("Aucune ville trouvée avec l'identifiant: " + id);

        return city;
    }
}
