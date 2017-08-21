package example.moviedb.controller;

import example.moviedb.dto.AddMovieDto;
import example.moviedb.dto.DeleteMovieDto;
import example.moviedb.dto.EditMovieDto;
import example.moviedb.dto.MovieDto;
import example.moviedb.model.Movie;
import example.moviedb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(value = "/movie")
    public List<MovieDto> getAllMovies(Principal currentUser) throws Exception {

        List<Movie> movies = movieService.getAllMovies(
                currentUser.getName()
        );
        return convertToDtos(movies);
    }

    @GetMapping(value = "/movie/{watched}")
    public List<MovieDto> getAllMovies(@PathVariable Boolean watched, Principal currentUser) throws Exception {

        List<Movie> movies = movieService.getMoviesByWatched(
                currentUser.getName(),
                watched
        );
        return convertToDtos(movies);
    }

    @PostMapping(value = "/movie/add")
    public UUID addMovie(@RequestBody AddMovieDto addMovieDto, Principal currentUser) throws Exception {

        return movieService.addMovie(
                currentUser.getName(),
                addMovieDto.getTitle(),
                addMovieDto.getDescription(),
                addMovieDto.getWatched()
        );
    }

    @PutMapping(value = "/movie/edit")
    public Boolean editMovie(@RequestBody EditMovieDto editMovieDto, Principal currentUser) throws Exception {

        return movieService.editMovie(
                currentUser.getName(),
                editMovieDto.getId(),
                editMovieDto.getTitle(),
                editMovieDto.getDescription(),
                editMovieDto.getWatched()
        );
    }

    @DeleteMapping(value = "/movie/delete")
    public Boolean deleteMovie(@RequestBody DeleteMovieDto deleteMovieDto, Principal currentUser) throws Exception {

        return movieService.deleteMovie(
                currentUser.getName(),
                deleteMovieDto.getId()
        );
    }

    private List<MovieDto> convertToDtos(List<Movie> movies) {
        //TODO this can be in separate converter class
        return movies.parallelStream()
                .map(movie -> new MovieDto(movie))
                .collect(Collectors.toList());
    }

}
