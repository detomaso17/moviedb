package example.moviedb.repository;

import example.moviedb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {

    public List<Movie> findAllMoviesByUser_Username(String username);

    @Query("select m from Movie m where m.user.username = ?1 and m.watched = ?2")
    public List<Movie> findMoviesByWatched(String username, Boolean watched);

}
