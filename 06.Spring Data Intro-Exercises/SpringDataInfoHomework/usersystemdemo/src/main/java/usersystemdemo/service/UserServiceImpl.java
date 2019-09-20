package usersystemdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usersystemdemo.domain.entities.User;
import usersystemdemo.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<String> getUserByEmailProvider(String provider) {
        String wildCard = "%" + provider;
        List<User> users = this.userRepository.findByEmail(wildCard);
        return users.stream()
                .map(u -> String.format("%s | %s", u.getUserName(), u.getEmail())).collect(Collectors.toList());
    }


    @Override
    public List<String> deleteInactiveUserBeforeDate() {
        List<User> users = this.userRepository.findAllByLastLogInBefore(LocalDateTime.now());
        for (User u : users) {
            u.setDeletedUser(true);
            this.userRepository.saveAndFlush(u);
        }

        List<User> deletedChecked = this.userRepository.findAllByDeletedUserIs(true);

        this.userRepository.findAllByDeletedUserIs(true).stream()
                .forEach(u -> this.userRepository.delete(u));

        return deletedChecked.stream().map(u -> String.format("User %s %s is Deleted", u.getFirstName(), u.getLastName())).collect(Collectors.toList());
    }
}
