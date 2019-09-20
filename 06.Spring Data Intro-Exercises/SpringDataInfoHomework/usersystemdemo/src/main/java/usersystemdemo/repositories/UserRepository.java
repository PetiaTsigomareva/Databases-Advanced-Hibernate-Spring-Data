package usersystemdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import usersystemdemo.domain.entities.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Task01
    @Query("SELECT u " +
            "FROM usersystemdemo.domain.entities.User As u " +
            "WHERE u.email " +
            "LIKE :wildCard ")
    List<User> findByEmail(@Param("wildCard") String wildCard);

    //Task02
    List<User> findAllByLastLogInBefore(LocalDateTime dateTime);

    List<User> findAllByDeletedUserIs(boolean a);

}
