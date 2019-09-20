package usersystemdemo.domain.entities;

import javax.persistence.*;


@MappedSuperclass
public class Town extends BaseEntity {
    private String name;
    private String country;
    private User user;

    public Town() {
    }

    public Town(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Column(name = "town_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "country_name")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @OneToOne()
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
