package gamestore.service;

import gamestore.config.MapperConfiguration;
import gamestore.domain.dtos.*;
import gamestore.domain.entities.Game;
import gamestore.domain.entities.Role;
import gamestore.domain.entities.User;
import gamestore.repository.GameRepository;
import gamestore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {
        Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();

        StringBuilder sb = new StringBuilder();
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            sb.append("Passwords don't match.");
        } else if (validator.validate(userRegisterDto).size() > 0) {
            for (ConstraintViolation<UserRegisterDto> violation : validator.validate(userRegisterDto)) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        } else {
            User entity = this.userRepository.findByEmail(userRegisterDto.getEmail()).orElse(null);

            if (entity != null) {
                sb.append("User already exists.");
                return sb.toString();
            }

            entity = this.modelMapper.map(userRegisterDto, User.class);

            if (this.userRepository.count() == 0) {
                entity.setRole(Role.ADMIN);
            } else {
                entity.setRole(Role.USER);
            }

            this.userRepository.saveAndFlush(entity);

            sb.append(String.format("%s was registered", entity.getFullName()));
        }

        return sb.toString().trim();
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {
        Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();

        Set<ConstraintViolation<UserLoginDto>> violations = validator.validate(userLoginDto);

        StringBuilder sb = new StringBuilder();
        if (violations.size() > 0) {
            for (ConstraintViolation<UserLoginDto> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        } else {
            User entity = this.userRepository.findByEmail(userLoginDto.getEmail()).orElse(null);

            if (entity == null) {
                return sb.append("User does not exist.").append(System.lineSeparator()).toString();
            } else if (!entity.getPassword().equals(userLoginDto.getPassword())) {
                return sb.append("Wrong password.").append(System.lineSeparator()).toString();
            }

            sb.append(String.format("Successfully logged in %s", entity.getFullName())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public String logoutUser(UserLogoutDto userLogoutDto) {
        StringBuilder sb = new StringBuilder();
        User entity = this.userRepository.findByEmail(userLogoutDto.getEmail()).orElse(null);

        if (entity == null) {
            return sb.append("User does not exist.").append(System.lineSeparator()).toString();
        }

        sb.append(String.format("User %s successfully logged out", entity.getFullName()));

        return sb.toString();
    }

    @Override
    public boolean isAdmin(String email) {
        User entity = this.userRepository.findByEmail(email).orElse(null);

        if (entity != null) {
            return entity.getRole().equals(Role.ADMIN);
        }

        return false;
    }

    @Override
    public String addGame(GameDto gameAddDto) {
        Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();

        StringBuilder sb = new StringBuilder();
        Game entity = this.modelMapper.map(gameAddDto, Game.class);

        if (validator.validate(entity).size() > 0) {
            for (ConstraintViolation<Game> violation : validator.validate(entity)) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        } else {
            this.gameRepository.saveAndFlush(entity);
            sb.append("Added " + entity.getTitle());
        }


        return sb.toString();
    }

    @Override
    public String editGame(String id, GameDto inputObj) {
        Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();

        StringBuilder sb = new StringBuilder();

        Game entity = this.gameRepository.findById(id).orElse(null);
        if (entity != null) {
            //TODO create custom mapper
            // MapperConfiguration.modelMapper().map(entity, Game.class);
            entity = changeByInput(inputObj, entity);

            Set<ConstraintViolation<Game>> validationErrors = validator.validate(entity);

            if (validationErrors.size() > 0) {
                for (ConstraintViolation<Game> violation : validationErrors) {
                    sb.append(violation.getMessage()).append(System.lineSeparator());
                }
            } else {
                this.gameRepository.saveAndFlush(entity);
                sb.append("Edited " + entity.getTitle());
            }
        } else {
            sb.append("Game does not exist!");
        }

        return sb.toString();
    }

    @Override
    public String deleteGame(String id) {
        StringBuilder sb = new StringBuilder();
        Game entity = this.gameRepository.findById(id).orElse(null);
        if (entity != null) {
            this.gameRepository.delete(entity);
            sb.append("Deleted " + entity.getTitle());
        } else {
            sb.append("Game does not exist!");
        }
        return sb.toString();
    }

    @Override
    public List<GameItemDto> getUserGames(String loggedInUser) {
        User user = this.userRepository.findByEmail(loggedInUser).orElse(null);

        Type listOfGameItemDTOs = new TypeToken<List<GameItemDto>>() {
        }.getType();

        return this.modelMapper.map(user.getGames(), listOfGameItemDTOs);

    }


    private Game changeByInput(GameDto inputObj, Game entity) {
        if (inputObj.getTitle() != null) {
            entity.setTitle(inputObj.getTitle());
        }

        if (inputObj.getTrailer() != null) {
            entity.setTrailer(inputObj.getTrailer());
        }

        if (inputObj.getImageThumbnail() != null) {
            entity.setImageThumbnail(inputObj.getImageThumbnail());
        }

        if (inputObj.getSize() != null) {
            entity.setSize(inputObj.getSize());
        }

        if (inputObj.getPrice() != null) {
            entity.setPrice(inputObj.getPrice());
        }

        if (inputObj.getDescription() != null) {
            entity.setDescription(inputObj.getDescription());
        }

        if (inputObj.getReleaseDate() != null) {
            entity.setReleaseDate(inputObj.getReleaseDate());
        }

        return entity;
    }
}

