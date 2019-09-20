package gamestore.domain.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GameDto {
    private String title;
    private String trailer;
    private Double size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;
    private String imageThumbnail;

    public GameDto() {
    }

    public GameDto(String title, String trailer, Double size, BigDecimal price, String description, LocalDate releaseDate, String imageThumbnail) {
        this.title = title;
        this.trailer = trailer;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
        this.imageThumbnail = imageThumbnail;
    }


    public GameDto(String title, String trailer, Double size, BigDecimal price, String description,LocalDate releaseDate) {
        this.title = title;
        this.trailer = trailer;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game:").append("\n").append("title: " + title).append("\n").append("trailer: " + trailer).append("\n").append("size: " + size).append("\n").append("price:" + price).append("\n").append("description: " + description);
        return sb.toString();
    }

}
