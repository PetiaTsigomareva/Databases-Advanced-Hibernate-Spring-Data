package usersystemdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import usersystemdemo.service.UserService;

import java.util.Scanner;

@Controller
public class UserSystemController implements CommandLineRunner {
    private final UserService userService;

    @Autowired
    public UserSystemController(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {
        //Query1
        this.getUserByEmailProvider(getScanner().nextLine());

        //Query2
      //  this.getInactiveUserBeforeDate();

    }

    public void getUserByEmailProvider(String provider) {
        this.userService.getUserByEmailProvider(provider)
                .stream().forEach(u -> System.out.println(u));

    }

    public void getInactiveUserBeforeDate() {
        this.userService.deleteInactiveUserBeforeDate().stream().forEach(u -> System.out.println(u));
    }

    private static Scanner getScanner() {

        return new Scanner(System.in);
    }
}
