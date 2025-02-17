package mg.itu.ticketing.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.spi.PersistenceProvider;
import mg.matsd.javaframework.core.utils.Assert;
import mg.matsd.javaframework.core.utils.StringUtils;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public final class DatabaseUtils {
    public  static final EntityManagerFactory ENTITY_MANAGER_FACTORY;
    private static final PersistenceProvider PERSISTENCE_PROVIDER = new HibernatePersistenceProvider();
    private static final Map<String, String> PROPERTIES;

    static {
        PROPERTIES = new HashMap<>();

        final String databaseUrl = System.getenv("DATABASE_URL");
        final String databaseUsername = System.getenv("DATABASE_USERNAME");
        final String databasePassword = System.getenv("DATABASE_PASSWORD");

        Assert.state(!StringUtils.isNullOrBlank(databaseUrl) &&
            !StringUtils.isNullOrBlank(databaseUsername) &&
            !StringUtils.isNullOrBlank(databasePassword),
            "Les variables d'environnement de la base de données ne sont pas définies");

        PROPERTIES.put("hibernate.connection.url", databaseUrl);
        PROPERTIES.put("hibernate.connection.username", databaseUsername);
        PROPERTIES.put("hibernate.connection.password", databasePassword);
        PROPERTIES.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        PROPERTIES.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        PROPERTIES.put("hibernate.show_sql", "true");
        PROPERTIES.put("hibernate.format_sql", "true");
        PROPERTIES.put("hibernate.physical_naming_strategy",
            "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        ENTITY_MANAGER_FACTORY = PERSISTENCE_PROVIDER.createEntityManagerFactory("default", PROPERTIES);

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
