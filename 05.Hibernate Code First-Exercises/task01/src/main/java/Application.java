import entities.WizardDeposits;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application{
    public static void main(String[] args) {


        EntityManagerFactory entityManagerFactory = Persistence.
                createEntityManagerFactory("gringotts");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        WizardDeposits deposits=new WizardDeposits("Petia","Tsigomareva");
        inTransaction(
              entityManager,
                () -> entityManager.persist(deposits));
    }

    static void inTransaction(EntityManager entityManager, Runnable runnable) {
        entityManager.getTransaction().begin();
        runnable.run();
        entityManager.getTransaction().commit();
    }


}
