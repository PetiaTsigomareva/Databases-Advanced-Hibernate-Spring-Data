package gamestore.service;

import gamestore.domain.dtos.GameDto;
import gamestore.domain.dtos.GameItemDto;

import java.util.List;

public interface GameService {
    List<GameItemDto> getAllGames();

    GameDto getGameByTitle(String title);
}
