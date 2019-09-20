package entities;

import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity(name = "people")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {

    private long id;
    private String firstName;
    private String lastName;
    private int age;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @Column(length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(length = 60,nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
@Column(nullable = false)
@Check(constraints = "age>0")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
