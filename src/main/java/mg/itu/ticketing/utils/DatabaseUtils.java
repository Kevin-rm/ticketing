package mg.itu.ticketing.utils;

import jakarta.persistence.*;
import mg.matsd.javaframework.core.utils.Assert;
import mg.matsd.javaframework.core.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public final class DatabaseUtils {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    static {
        Map<String, String> additionalProperties = new HashMap<>();

        final String databaseUrl = System.getenv("DATABASE_URL");
        final String databaseUsername = System.getenv("DATABASE_USERNAME");
        final String databasePassword = System.getenv("DATABASE_PASSWORD");

        Assert.state(!StringUtils.isNullOrBlank(databaseUrl) &&
            !StringUtils.isNullOrBlank(databaseUsername) &&
            !StringUtils.isNullOrBlank(databasePassword),
            "Les variables d'environnement de la base de données ne sont pas définies");

        additionalProperties.put("hibernate.connection.url", databaseUrl);
        additionalProperties.put("hibernate.connection.username", databaseUsername);
        additionalProperties.put("hibernate.connection.password", databasePassword);

        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("default", additionalProperties);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (ENTITY_MANAGER_FACTORY == null || !ENTITY_MANAGER_FACTORY.isOpen()) return;
            ENTITY_MANAGER_FACTORY.close();
        }));
    }

    private DatabaseUtils() { }

    public static EntityManager entityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static <T> T execute(Function<EntityManager, T> function) {
        try (EntityManager entityManager = entityManager()) {
            return function.apply(entityManager);
        }
    }

    public static void executeTransactional(Consumer<EntityManager> consumer) {
        execute(entityManager -> {
            executeTransactional(entityManager, consumer);
            return null;
        });
    }

    public static void executeTransactional(EntityManager entityManager, Consumer<EntityManager> consumer) {
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
