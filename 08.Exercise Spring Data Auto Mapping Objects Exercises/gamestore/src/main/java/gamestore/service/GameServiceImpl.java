package gamestore.service;

import gamestore.config.MapperConfiguration;
import gamestore.domain.dtos.GameDto;
import gamestore.domain.dtos.GameItemDto;
import gamestore.domain.entities.Game;
import gamestore.repository.GameRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;

        this.modelMapper = modelMapper;
    }

    @Override
    public List<GameItemDto> getAllGames() {
        List<Game> games = this.gameRepository.findAll();
        Type listOfGameItemDTOs = new TypeToken<List<GameItemDto>>() {
        }.getType();

        return this.modelMapper.map(games, listOfGameItemDTOs);
    }

    @Override
    public GameDto getGameByTitle(String title) {
        Game game = this.gameRepository.findByTitle(title);

        return this.modelMapper.map(game, GameDto.class);
    }


}
