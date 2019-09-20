package usersystemdemo.domain.entities;

import javax.persistence.Entity;

@Entity(name = "Born_Towns")
public class BornTown extends Town {
    public BornTown(String name, String country) {
        super(name, country);
    }
}
