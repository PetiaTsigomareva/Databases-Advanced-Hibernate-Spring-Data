package usersystemdemo.service;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

    List<String> getUserByEmailProvider(String provider);
    List<String> deleteInactiveUserBeforeDate();

}
