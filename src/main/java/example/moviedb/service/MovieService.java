package example.moviedb.service;

import example.moviedb.model.Movie;
import example.moviedb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies(String username) {

        //TODO implement
        return null;
    }

    public List<Movie> getMoviesByWatched(String username, Boolean watched) {

        //TODO implement
        return null;
    }

    public UUID addMovie(String username,
                         String title,
                         String description,
                         Boolean watched) {

        //TODO implement
        return null;
    }

    public Boolean editMovie(String username,
                             String id,
                             String title,
                             String description,
                             Boolean watched) {

        //TODO implement
        return false;
    }

    public Boolean deleteMovie(String username,
                               String id) {

        //TODO implement
        return false;
    }
}
