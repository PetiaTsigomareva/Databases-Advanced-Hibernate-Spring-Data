import entities.Course;
import entities.Person;
import entities.Student;
import entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {
    public static void main(String[] args) {


        EntityManagerFactory entityManagerFactory = Persistence.
                createEntityManagerFactory("university_system");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Student student = new Student("Ivan", "Petrov", "123503", 5.50, 30);
        Teacher teacher = new Teacher("Petia", "Petrova", "0887563215", "dsfs@abv.bg", 10);

        inTransaction(
                entityManager,
                () -> entityManager.persist(student));

        inTransaction(
                entityManager,
                () -> entityManager.persist(teacher));


        Course course = new Course("DB Advance", "sgsadgagagag", 60, teacher);

        inTransaction(
                entityManager,
                () -> entityManager.persist(course));


    }

    static void inTransaction(EntityManager entityManager, Runnable runnable) {
        entityManager.getTransaction().begin();
        runnable.run();
        entityManager.getTransaction().commit();
    }


}
