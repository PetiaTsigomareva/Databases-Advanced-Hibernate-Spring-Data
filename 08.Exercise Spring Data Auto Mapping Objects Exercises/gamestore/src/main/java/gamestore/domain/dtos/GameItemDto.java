package gamestore.domain.dtos;

import gamestore.domain.entities.Game;
import gamestore.mapping.CustomMappings;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Set;

public class GameItemDto {
    private String title;
    private BigDecimal price;

    public GameItemDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' + ", price=" + price;
    }
//TODO custom mapper
//    @Override
//    public void configureMappings(ModelMapper mapper) {
//        Converter<Set, Integer> countConverter =
//                ctx -> ctx.getSource().size();
//
//        mapper.createTypeMap(Game.class, GameItemDto.class)
//                .addMappings(map -> map
//                        .using(countConverter)
//                        .map(
//                                Game::getTitle,
//                                GameItemDto::setTitle
//                        )
//                );
//
//    }
}
