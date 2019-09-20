package usersystemdemo.domain.entities;

import javax.persistence.Entity;

@Entity(name = "Currently_Living_Towns")
public class CurrentlyLivingTown extends Town {
    public CurrentlyLivingTown(String name, String country) {
        super(name, country);
    }
}
