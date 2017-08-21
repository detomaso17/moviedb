package example.moviedb.dto;

import lombok.Data;

@Data
public class EditMovieDto {

    private String id;
    private String title;
    private String description;
    private Boolean watched;
}
