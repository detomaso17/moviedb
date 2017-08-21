package example.moviedb.service;

import example.moviedb.model.Movie;
import example.moviedb.model.User;
import example.moviedb.repository.MovieRepository;
import example.moviedb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {

    private MovieRepository movieRepository;
    private UserRepository userRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public List<Movie> getAllMovies(String username) {

        return movieRepository.findAllMoviesByUser_Username(username);
    }

    public List<Movie> getMoviesByWatched(String username, Boolean watched) {

        return movieRepository.findMoviesByWatched(username, watched);
    }

    public UUID addMovie(String username,
                         String title,
                         String description,
                         Boolean watched) throws Exception {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new Exception("no user with username: " + username + " found");
        }

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setWatched(watched);
        movie.setUser(user);

        Movie savedMovie = movieRepository.save(movie);

        return savedMovie.getId();
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
