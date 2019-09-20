package gamestore.web.controllers;

import gamestore.domain.dtos.*;
import gamestore.service.GameService;
import gamestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {

    private String loggedInUser;
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public GameStoreController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String inputLine = scanner.nextLine();

            String[] inputParams = inputLine.split("\\|");
            switch (inputParams[0]) {
                case "RegisterUser":
                    UserRegisterDto userRegisterDto = new UserRegisterDto(inputParams[1], inputParams[2], inputParams[3], inputParams[4]);

                    System.out.println(this.userService.registerUser(userRegisterDto));
                    break;
                case "LoginUser":
                    if (this.loggedInUser == null) {
                        UserLoginDto userLoginDto = new UserLoginDto(inputParams[1], inputParams[2]);

                        String loginResult = this.userService.loginUser(userLoginDto);

                        if (loginResult.contains("Successfully logged in")) {
                            this.loggedInUser = userLoginDto.getEmail();
                        }

                        System.out.println(loginResult);
                    } else {
                        System.out.println("Session is taken.");
                    }

                    break;
                case "Logout":
                    if (this.loggedInUser == null) {
                        System.out.println("Cannot log out. No user was logged in.");
                    } else {
                        String logoutResult = this.userService.logoutUser(new UserLogoutDto(this.loggedInUser));
                        System.out.println(logoutResult);

                        this.loggedInUser = null;
                    }
                    break;
                case "AddGame":
                    if (this.loggedInUser != null) {
                        if (this.userService.isAdmin(this.loggedInUser)) {
                            //TODO add game imageThumbnail
                            //AddGame|<title>|<price>|<size>|<trailer>|<thubnailURL>|<description>|<releaseDate>
                            //public GameDto(String title, String trailer,Double size, BigDecimal price, String description, LocalDate releaseDate,String imageThumbnail)
                            LocalDate releaseDate = LocalDate.parse(inputParams[6], DateTimeFormatter.ofPattern("d-M-yyyy"));
                            //  GameDto gameAddDto = new GameDto(inputParams[1], inputParams[4], Double.parseDouble(inputParams[3]), BigDecimal.valueOf(Double.parseDouble(inputParams[2])), inputParams[6], releaseDate, inputParams[5]);
                            GameDto gameAddDto = new GameDto(inputParams[1], inputParams[4], Double.parseDouble(inputParams[3]), BigDecimal.valueOf(Double.parseDouble(inputParams[2])), inputParams[5], releaseDate);
                            System.out.println(this.userService.addGame(gameAddDto));

                        } else {
                            System.out.println("User is not Admin - can not add games.");
                        }

                    } else {
                        System.out.println("User is not LoggedIn - can not add game");
                    }
                    break;
                case "EditGame":
                    if (this.loggedInUser != null) {
                        if (this.userService.isAdmin(this.loggedInUser)) {

                            String id = inputParams[1];
                            GameDto gameEditDto = getObjectByInput(inputParams);

                            System.out.println(this.userService.editGame(id, gameEditDto));

                        } else {
                            System.out.println("User is not Admin - can not edit games.");
                        }

                    } else {
                        System.out.println("User is not LoggedIn - can not edit game");
                    }
                    break;
                case "DeleteGame":
                    if (this.loggedInUser != null) {
                        if (this.userService.isAdmin(this.loggedInUser)) {

                            String id = inputParams[1];
                            System.out.println(this.userService.deleteGame(id));

                        } else {
                            System.out.println("User is not Admin - can not delete games.");
                        }

                    } else {
                        System.out.println("User is not LoggedIn - can not edit game");
                    }
                    break;
                case "AllGame":
                    List<GameItemDto> games = getAllGames(this.gameService);
                    games.stream().forEach(g -> System.out.println(g));
                    break;
                case "DetailGame":
                    GameDto game = getGameByTitle(this.gameService, inputParams[1]);
                    System.out.println(game.toString());
                    break;
                case "OwnedGame":
                    if (this.loggedInUser != null) {

                        List<GameItemDto> userGames = getUserGames(this.userService, this.loggedInUser);
                        userGames.stream().forEach(g -> System.out.println(g.getTitle()));
                    } else {
                        System.out.println("User is not LoggedIn - can not view user games!");
                    }
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;
            }

            if (inputParams[0].equals("End")) {
                break;
            }
        }
    }

    private List<GameItemDto> getUserGames(UserService userService, String loggedInUser) {
        List<GameItemDto> result = new ArrayList<GameItemDto>();
        result = userService.getUserGames(loggedInUser);
        return result;

    }

    private GameDto getGameByTitle(GameService gameService, String title) {
        GameDto game = gameService.getGameByTitle(title);
        return game;
    }

    private List<GameItemDto> getAllGames(GameService gameService) {
        List<GameItemDto> result = new ArrayList<GameItemDto>();
        result = gameService.getAllGames();

        return result;

    }

    private GameDto getObjectByInput(String[] inputParams) {
        GameDto result = new GameDto();
        for (String s : inputParams) {
            if (s.contains("=")) {
                String[] input = s.split("=");
                if (input[0].contains("title")) {
                    result.setTitle(input[1]);
                } else if (input[0].contains("trailer")) {
                    result.setTrailer(input[1]);

                } else if (input[0].contains("imageThumbnail")) {
                    result.setImageThumbnail(input[1]);

                } else if (input[0].contains("size")) {
                    result.setSize(Double.parseDouble(input[1]));

                } else if (input[0].contains("price")) {
                    result.setPrice(BigDecimal.valueOf(Double.parseDouble(input[1])));

                } else if (input[0].contains("description")) {
                    result.setDescription(input[1]);

                } else if (input[0].contains("releaseDate")) {
                    result.setReleaseDate(LocalDate.parse(inputParams[1], DateTimeFormatter.ofPattern("d-M-yyyy")));

                }
            }

        }

        return result;
    }


}
