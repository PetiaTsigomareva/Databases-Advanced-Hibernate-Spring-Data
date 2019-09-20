package entities;

import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity(name = "info")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Info {

    private Long id;
    private String name;


    public Info() {
    }

    public Info(String name) {
        this.name = name;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   @Column(name = "name",length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
