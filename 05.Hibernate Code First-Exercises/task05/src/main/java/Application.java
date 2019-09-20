import entities.BankAccount;
import entities.CreditCard;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {
    public static void main(String[] args) {


        EntityManagerFactory entityManagerFactory = Persistence.
                createEntityManagerFactory("bills_payment_system");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User u = new User("Ivan", "Petrov");
        CreditCard c = new CreditCard(123654802, u, "Visa");
        BankAccount b = new BankAccount(1235698014, u, "FirsrBan", "AA582Gdf23");


        inTransaction(
                entityManager,
                () -> entityManager.persist(u));

        inTransaction(
                entityManager,
                () -> entityManager.persist(c));


        inTransaction(
                entityManager,
                () -> entityManager.persist(b));


    }

    static void inTransaction(EntityManager entityManager, Runnable runnable) {
        entityManager.getTransaction().begin();
        runnable.run();
        entityManager.getTransaction().commit();
    }


}
