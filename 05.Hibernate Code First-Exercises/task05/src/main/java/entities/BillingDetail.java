package entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity(name = "billing_detais")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public abstract class BillingDetail {

    private Long id;
    private int number;
    private User user;

    public BillingDetail() {
    }

    public BillingDetail(int number, User user) {
        this.number = number;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
