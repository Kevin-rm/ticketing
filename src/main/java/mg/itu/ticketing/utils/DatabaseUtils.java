package mg.itu.ticketing.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;

public final class DatabaseUtils {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("default");

    private DatabaseUtils() { }

    public static EntityManager entityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void execute(Consumer<EntityManager> consumer) {
        try (EntityManager entityManager = entityManager()) {
            consumer.accept(entityManager);
        }
    }

    public static void executeWithTransaction(Consumer<EntityManager> consumer) {
        execute(entityManager -> executeWithTransaction(entityManager, consumer));
    }

    private static void executeWithTransaction(EntityManager entityManager, Consumer<EntityManager> consumer) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            consumer.accept(entityManager);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) entityTransaction.rollback();
            throw e;
        }
    }
}
