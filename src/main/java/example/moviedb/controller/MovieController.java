package example.moviedb.controller;

import example.moviedb.dto.AddMovieDto;
import example.moviedb.dto.DeleteMovieDto;
import example.moviedb.dto.EditMovieDto;
import example.moviedb.model.Movie;
import example.moviedb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(value = "/movie")
    public List<Movie> getAllMovies(Principal currentUser) throws Exception {

        return movieService.getAllMovies(
                currentUser.getName()
        );
    }

    @GetMapping(value = "/movie/{watched}")
    public List<Movie> getAllMovies(@PathVariable Boolean watched, Principal currentUser) throws Exception {

        return movieService.getMoviesByWatched(
                currentUser.getName(),
                watched
        );
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

    @PostMapping(value = "/movie/edit")
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
}
