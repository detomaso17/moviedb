package example.moviedb.service;

import example.moviedb.model.Movie;
import example.moviedb.repository.MovieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

    private final static String USERNAME = "newUser";

    private final static String MOVIE_ID = "4322432876490356";
    private final static String MOVIE_WRONG_ID = "4322432876490351";
    private final static String MOVIE_TITLE = "newMovie";
    private final static String MOVIE_PREVIOUS_DESCRIPTION = "previous description";
    private final static String MOVIE_NEW_DESCRIPTION = "new description";

    private final static int MOVIES_COUNT = 10;

    @Mock
    private MovieRepository movieRepository;


    private MovieService movieService;

    @Before
    public void setUp() throws Exception {

        movieService = new MovieService(movieRepository);
    }

    @Test
    public void shouldAddMovie() throws Exception {

        when(movieRepository.save(any(Movie.class))).thenReturn(mock(Movie.class));
        UUID movieUuid = movieService.addMovie(USERNAME,
                MOVIE_TITLE,
                MOVIE_NEW_DESCRIPTION,
                true);

        assertNotNull(movieUuid);
        verify(movieRepository).save(any(Movie.class));

    }

    @Test
    public void shouldEditMovie() throws Exception {

        Movie editedMovie = new Movie(MOVIE_TITLE, true);
        editedMovie.setDescription(MOVIE_PREVIOUS_DESCRIPTION);

        when(movieRepository.findMovieById(UUID.fromString(MOVIE_ID))).thenReturn(editedMovie);
        when(movieRepository.save(editedMovie)).thenReturn(editedMovie);

        Boolean isEdited = movieService.editMovie(USERNAME,
                MOVIE_ID,
                MOVIE_TITLE,
                MOVIE_NEW_DESCRIPTION,
                false);

        assertTrue(isEdited);
        assertEquals(MOVIE_TITLE, editedMovie.getTitle());
        assertEquals(MOVIE_NEW_DESCRIPTION, editedMovie.getDescription());
        assertEquals(false, editedMovie.getWatched());

        verify(movieRepository).save(editedMovie);
    }

    @Test(expected = Exception.class)
    public void shouldNotEditMovieDueToWrongId() throws Exception {

        Movie editedMovie = new Movie(MOVIE_TITLE, true);
        editedMovie.setDescription(MOVIE_PREVIOUS_DESCRIPTION);

        when(movieRepository.findMovieById(UUID.fromString(MOVIE_ID))).thenReturn(editedMovie);
        when(movieRepository.save(editedMovie)).thenReturn(editedMovie);

        when(movieRepository.save(any(Movie.class))).thenReturn(any(Movie.class));

        movieService.editMovie(USERNAME,
                MOVIE_WRONG_ID,
                MOVIE_TITLE,
                MOVIE_NEW_DESCRIPTION,
                false);
    }

    @Test
    public void shouldDeleteMovie() throws Exception {

        Movie mockMovie = mock(Movie.class);

        when(movieRepository.findMovieById(UUID.fromString(MOVIE_ID))).thenReturn(mockMovie);

        Boolean isDeleted = movieService.deleteMovie(USERNAME, MOVIE_ID);

        assertTrue(isDeleted);
        verify(movieRepository).delete(mockMovie);
    }

    @Test(expected = Exception.class)
    public void shouldNotDeleteMovieDueToWrongId() throws Exception {

        Movie mockMovie = mock(Movie.class);

        when(movieRepository.findMovieById(UUID.fromString(MOVIE_ID))).thenReturn(mockMovie);

        movieService.deleteMovie(USERNAME, MOVIE_WRONG_ID);
    }

    @Test
    public void shouldListAllMovies() throws Exception {

        List<Movie> mockMovies = prepareMockMovies();

        when(movieRepository.findAllMoviesByUser_Username(USERNAME)).thenReturn(mockMovies);

        List<Movie> allMovies = movieService.getAllMovies(USERNAME);

        assertEquals(mockMovies, allMovies);
        verify(movieRepository).findAllMoviesByUser_Username(USERNAME);
    }

    @Test
    public void shouldListAllWatchedMovies() throws Exception {

        List<Movie> mockWatchedMovies = prepareMockMovies();
        List<Movie> mockNotWatchedMovies = prepareMockMovies();

        when(movieRepository.findMoviesByWatched(USERNAME, true)).thenReturn(mockWatchedMovies);
        when(movieRepository.findMoviesByWatched(USERNAME, false)).thenReturn(mockNotWatchedMovies);

        List<Movie> watchedMovies = movieService.getMoviesByWatched(USERNAME, true);

        assertEquals(mockWatchedMovies, watchedMovies);
        verify(movieRepository).findMoviesByWatched(USERNAME, true);
        verify(movieRepository, new Times(0)).findMoviesByWatched(USERNAME, false);
    }

    @Test
    public void shouldListAllNotWatchedMovies() throws Exception {

        List<Movie> mockWatchedMovies = prepareMockMovies();
        List<Movie> mockNotWatchedMovies = prepareMockMovies();

        when(movieRepository.findMoviesByWatched(USERNAME, true)).thenReturn(mockWatchedMovies);
        when(movieRepository.findMoviesByWatched(USERNAME, false)).thenReturn(mockNotWatchedMovies);

        List<Movie> unwatchedMovies = movieService.getMoviesByWatched(USERNAME, false);

        assertEquals(mockNotWatchedMovies, unwatchedMovies);
        verify(movieRepository).findMoviesByWatched(USERNAME, false);
        verify(movieRepository, new Times(0)).findMoviesByWatched(USERNAME, true);
    }


    private List<Movie> prepareMockMovies() {

        List<Movie> mockMovies = new ArrayList<>();

        for (int i = 0; i < MOVIES_COUNT; i++) {
            mockMovies.add(mock(Movie.class));
        }

        return mockMovies;
    }
}
