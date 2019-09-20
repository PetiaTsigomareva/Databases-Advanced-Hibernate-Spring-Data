import entities.Diagnose;
import entities.Medicament;
import entities.Patient;
import entities.Visitation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {
    public static void main(String[] args) {


        EntityManagerFactory entityManagerFactory = Persistence.
                createEntityManagerFactory("hospital");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Patient p = new Patient("Ivan", "Petrov");
        Diagnose d = new Diagnose("Lud", "djjdtjdt");
        Medicament m = new Medicament("Analgin");
        Visitation v = new Visitation("Visitacia1", p, d);
        v.getMedicaments().add(m);

        inTransaction(
                entityManager,
                () -> entityManager.persist(p));

        inTransaction(
                entityManager,
                () -> entityManager.persist(d));


        inTransaction(
                entityManager,
                () -> entityManager.persist(m));

        inTransaction(
                entityManager,
                () -> entityManager.persist(v));


    }

    static void inTransaction(EntityManager entityManager, Runnable runnable) {
        entityManager.getTransaction().begin();
        runnable.run();
        entityManager.getTransaction().commit();
    }


}
