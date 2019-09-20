package usersystemdemo.domain.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String fullName;
    private String userName;

    private String password;
    private String email;
    private String profilePicture;
    private LocalDateTime registrationDate;
    private LocalDateTime lastLogIn;
    private int age;
    private boolean isDeletedUser;
    private BornTown bornTown;
    private CurrentlyLivingTown currentlyLivingTown;
    private Set<User> friends;
    private Set<Album> albums;

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.registrationDate = LocalDateTime.now();
        this.lastLogIn = LocalDateTime.now();
        this.friends = new HashSet<User>();
        this.albums = new HashSet<Album>();

    }

    @Column(name = "first_name", length = 30)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 30)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Transient
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "user_name", length = 30)
    @NotNull
    @Length(min = 4, max = 30)

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "password", length = 50)
    @NotNull
    @Length(min = 6, max = 50)

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email")
    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "profile_picture")

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Column(name = "registered_on")
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Column(name = "last_time_loged_in")
    public LocalDateTime getLastLogIn() {
        return lastLogIn;
    }

    public void setLastLogIn(LocalDateTime lastLogIn) {
        this.lastLogIn = lastLogIn;
    }

    @Column(name = "age")
    @Min(1)
    @Max(120)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "is_deleted")
    public boolean isDeletedUser() {
        return isDeletedUser;
    }

    public void setDeletedUser(boolean deletedUser) {
        isDeletedUser = deletedUser;
    }


    @OneToOne(mappedBy = "user", targetEntity = BornTown.class)
    public BornTown getBornTown() {
        return bornTown;
    }

    public void setBornTown(BornTown bornTown) {
        this.bornTown = bornTown;
    }

    @OneToOne(mappedBy = "user", targetEntity = CurrentlyLivingTown.class)
    public CurrentlyLivingTown getCurrentlyLivingTown() {
        return currentlyLivingTown;
    }

    public void setCurrentlyLivingTown(CurrentlyLivingTown currentlyLivingTown) {
        this.currentlyLivingTown = currentlyLivingTown;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @OneToMany(mappedBy = "user", targetEntity = Album.class)
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
}
