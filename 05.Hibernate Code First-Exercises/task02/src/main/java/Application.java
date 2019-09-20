import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {
    public static void main(String[] args) {


        EntityManagerFactory entityManagerFactory = Persistence.
                createEntityManagerFactory("sales");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Info[] infos = {
                new Product("Beer"),
                new Customer("Petia"),


        };
        inTransaction(
                entityManager,
                () ->entityManager.persist(infos[0]));

        inTransaction(
                entityManager,
                () ->entityManager.persist(infos[1]));



        StoreLocation location = new StoreLocation("Sofia");

        inTransaction(
                entityManager,
                () ->entityManager.persist(location));

        Sale sale = new Sale(infos[0],infos[1],location);

        inTransaction(
                entityManager,
                () -> entityManager.persist(sale));
    }

    static void inTransaction(EntityManager entityManager, Runnable runnable) {
        entityManager.getTransaction().begin();
        runnable.run();
        entityManager.getTransaction().commit();
    }


}
