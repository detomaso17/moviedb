package example.moviedb.dto;

import example.moviedb.model.Movie;
import lombok.Data;

@Data
public class MovieDto {

    private String uuid;
    private String title;
    private String description;
    private Boolean watched;

    public MovieDto(Movie movie) {

        this.uuid = movie.getId().toString();
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.watched = movie.getWatched();
    }
}
