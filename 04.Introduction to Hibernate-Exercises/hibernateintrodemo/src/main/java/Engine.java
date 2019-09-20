import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    public static final String ADDRESS = "Vitoshka 15";

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;


    }

    public void run() {
        try {
            // Enter number between 2 and 13 and Start selected task!!
            //!!! For some task have to enter addition input
            this.callTask(getScanner().nextInt());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void callTask(int taskNumber) throws SQLException {

        switch (taskNumber) {
            case 2:
                this.removeObject();
                break;
            case 3:
                this.containsEmployee();
                break;
            case 4:
                this.employeeWithSalaryOver50000();
                break;
            case 5:
                this.employeesFromDepartment();
                break;
            case 6:
                this.addingNewAddress();
                break;
            case 7:
                this.addressByEmployeeCount();
                break;
            case 8:
                this.getEmployeeWithProject();
                break;
            case 9:
                this.findLatestProject();
                break;
            case 10:
                this.increaseSalaries();
                break;
            case 11:
                this.removeTowns();
                break;
            case 12:
                this.findEmployeesByFirstName();
                break;
            case 13:
                this.employeesMaxSalaries();
                break;

            default:
                break;
        }
    }


    // Task 2 - Remove Objects

    private void removeObject() {
        entityManager.getTransaction().begin();
        try {
            List<Town> towns =
                    entityManager.createQuery("SELECT t FROM Town t WHERE LENGTH(t.name) > 5", Town.class)
                            .getResultList();

            towns.forEach(town -> {
                entityManager.detach(town);
                town.setName(town.getName().toLowerCase());
                entityManager.merge(town);
            });
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();

        }
    }


    //Task 3 - Contains Employee

    private void containsEmployee() {
        String name = getScanner().nextLine();
        entityManager.getTransaction().begin();
        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee as e WHERE concat(e.firstName, ' ' ,e.lastName) = :name", Employee.class)
                .setParameter("name", name)
                .getResultList();

        if (!employees.isEmpty()) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        entityManager.close();

    }



    //Task 4 - Employees with Salary Over 50 000

    private void employeeWithSalaryOver50000() {
        entityManager.getTransaction().begin();

        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e WHERE e.salary>50000", Employee.class)
                .getResultList();

        employees.forEach(e -> System.out.println(e.getFirstName()));

        entityManager.close();

    }


    //Task 5 - Employees From Department

    private void employeesFromDepartment() {
        entityManager.getTransaction().begin();
        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e WHERE e.department.name = 'Research and Development' ORDER BY e.salary, e.id", Employee.class)
                .getResultList();

        employees.forEach(e -> System.out.printf("%s %s from %s - $%.2f%n",
                e.getFirstName(), e.getLastName(),
                e.getDepartment().getName(), e.getSalary()));

        entityManager.close();

    }

    //Task 6 - Adding a New Address and Updating Employee

    private void addingNewAddress() {
        String employeeLastName = getScanner().nextLine();
        System.out.println(employeeLastName);
        entityManager.getTransaction().begin();

        try {
            Address address = new Address();
            address.setText(ADDRESS);
            entityManager.persist(address);

            Employee employee = entityManager.createQuery("SELECT e FROM Employee e WHERE e.lastName =:name", Employee.class)
                    .setParameter("name", employeeLastName)
                    .getSingleResult();


            employee.setAddress(address);

            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();

        }
    }


    //Task 7 - Address with Employee Count

    private void addressByEmployeeCount() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("SELECT a.text, t.name, count(emp) as emp_count FROM Employee as emp " +
                "JOIN emp.address as a " +
                "JOIN a.town as t " +
                "GROUP BY a.text, t.name " +
                "ORDER BY emp_count DESC, t.id, a.id ", Object[].class)
                .setMaxResults(10)
                .getResultList()
                .forEach(x -> System.out.printf("%s, %s - %s employees%n", x[0], x[1], x[2]));

        entityManager.close();


    }


    //Task 8 - Get Employee with Project

    private void getEmployeeWithProject() {
        entityManager.getTransaction().begin();
        int id = Integer.parseInt(getScanner().nextLine());
        Employee employee = entityManager.createQuery("SELECT e FROM Employee e WHERE e.id=:id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
        System.out.println(employee.getFirstName() + " " + employee.getLastName() + " - " + employee.getJobTitle());
        employee.getProjects().stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.println("->" + p.getName()));

        entityManager.close();
    }


    //  Task 9 - Find Latest 10 Projects

    private void findLatestProject() {
        entityManager.getTransaction().begin();
        List<Project> projects = entityManager.createQuery("SELECT p FROM Project p ORDER BY p.name", Project.class)
                .getResultList().subList(0, 10);
        projects.stream()
                .forEach(p -> {
                    System.out.println(String.format("Project name: %s", p.getName()));
                    System.out.println(String.format("        Project Description: %s", p.getDescription()));
                    System.out.println(String.format("        Project Start Date: %s", p.getStartDate()));
                    System.out.println(String.format("        Project End Date: %s", p.getEndDate()));
                });


        entityManager.close();
    }


    //Task 10 - Increase Salaries

    private void increaseSalaries() {
        entityManager.getTransaction().begin();

        for (Employee employee : entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services') " +
                "ORDER BY e.id", Employee.class)
                .getResultList()) {
            employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(1.12)));
            System.out.printf("%s %s($%.2f)%n", employee.getFirstName(), employee.getLastName(), employee.getSalary());
        }


        entityManager.close();

    }


    //Task 11 - Remove Towns

    private void removeTowns() {
        String townName = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            townName = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        entityManager.getTransaction().begin();
        try {
            Town town = entityManager.createQuery("SELECT t FROM Town t WHERE t.name =:name", Town.class)
                    .setParameter("name", townName)
                    .getSingleResult();

            List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a WHERE a.town =:town", Address.class)
                    .setParameter("town", town)
                    .getResultList();

            System.out.printf(addresses.size() == 1
                            ? "%d address in %s deleted%n"
                            : "%d addresses in %s deleted%n",
                    addresses.size(), townName);

            for (Iterator<Address> iterator = addresses.iterator(); iterator.hasNext(); ) {
                Address address = iterator.next();
                for (Iterator<Employee> iterator1 = address.getEmployees().iterator(); iterator1.hasNext(); ) {
                    Employee employee = iterator1.next();
                    employee.setAddress(null);
                }
                entityManager.remove(address);
            }

            entityManager.remove(town);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();

        }

    }


    //Task 12 - Find Employees by First Name

    private void findEmployeesByFirstName() {
        String pattern = getScanner().nextLine();
        pattern = pattern + "%";
        entityManager.getTransaction().begin();

        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e WHERE e.firstName LIKE :pattern", Employee.class)
                .setParameter("pattern", pattern)
                .getResultList();


        employees.forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                e.getFirstName(),
                e.getLastName(),
                e.getDepartment().getName(),
                e.getSalary()));

        entityManager.close();


    }


    //Task 13 - Employees Maximum Salaries

    private void employeesMaxSalaries() {
        try {
            entityManager.getTransaction().begin();
            List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e " +
                    "WHERE e.salary NOT BETWEEN 30000 AND 70000" +
                    "GROUP BY e.department.id ORDER BY e.salary DESC", Employee.class)
                    .getResultList();

            List<Employee> toSort = new ArrayList<>();
            for (Employee employee : employees) {
                toSort.add(employee);
            }
            toSort.sort(Comparator.comparing(e -> e.getDepartment().getId()));
            for (Employee employee : toSort) {
                System.out.printf("%s - %.2f%n",
                        employee.getDepartment().getName(), employee.getSalary());
            }


        } finally {
            entityManager.close();
        }
    }


    private static Scanner getScanner() {

        return new Scanner(System.in);
    }
}
