package gamestore.service;

import gamestore.domain.dtos.*;

import java.util.List;


public interface UserService {

    String registerUser(UserRegisterDto userRegisterDto);

    String loginUser(UserLoginDto userLoginDto);

    String logoutUser(UserLogoutDto userLogoutDto);

    boolean isAdmin(String email);

    String addGame(GameDto gameAddDto);

    String editGame(String id, GameDto gameEditDto);

    String deleteGame(String id);


    List<GameItemDto> getUserGames(String loggedInUser);
}
