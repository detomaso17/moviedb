package example.moviedb.dto;

import lombok.Data;

@Data
public class AddMovieDto {

    private String title;
    private String description;
    private Boolean watched;
}
