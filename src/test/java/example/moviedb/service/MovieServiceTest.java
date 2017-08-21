package example.moviedb.service;

import example.moviedb.model.Movie;
import example.moviedb.model.User;
import example.moviedb.repository.MovieRepository;
import example.moviedb.repository.UserRepository;
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

    private final static String MOVIE_UUID_TEXT = "12354568-4367-7899-1234-789445611234";
    private final static UUID MOVIE_UUID = UUID.fromString(MOVIE_UUID_TEXT);

    private final static String MOVIE_WRONG_UUID = "12354568-4367-7899-1234-789445611235";
    private final static String MOVIE_TITLE = "newMovie";
    private final static String MOVIE_PREVIOUS_DESCRIPTION = "previous description";
    private final static String MOVIE_NEW_DESCRIPTION = "new description";

    private final static int MOVIES_COUNT = 10;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserRepository userRepository;


    private MovieService movieService;

    @Before
    public void setUp() throws Exception {

        movieService = new MovieService(movieRepository, userRepository);
    }

    @Test
    public void shouldAddMovie() throws Exception {


        Movie savedMockMovie = mock(Movie.class);
        when(savedMockMovie.getId()).thenReturn(MOVIE_UUID);

        when(movieRepository.save(any(Movie.class))).thenReturn(savedMockMovie);
        when(userRepository.findByUsername(USERNAME)).thenReturn(mock(User.class));
        UUID newUuid = movieService.addMovie(USERNAME,
                MOVIE_TITLE,
                MOVIE_NEW_DESCRIPTION,
                true);

        assertNotNull(newUuid);
        assertEquals(MOVIE_UUID, newUuid);
        verify(movieRepository).save(any(Movie.class));
        verify(userRepository).findByUsername(USERNAME);
    }

    @Test
    public void shouldEditMovie() throws Exception {

        Movie editedMovie = new Movie(MOVIE_TITLE, true);
        editedMovie.setDescription(MOVIE_PREVIOUS_DESCRIPTION);

        when(movieRepository.findMovieById(MOVIE_UUID)).thenReturn(editedMovie);
        when(movieRepository.save(editedMovie)).thenReturn(editedMovie);

        Boolean isEdited = movieService.editMovie(USERNAME,
                MOVIE_UUID_TEXT,
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

        when(movieRepository.findMovieById(MOVIE_UUID)).thenReturn(editedMovie);
        when(movieRepository.save(editedMovie)).thenReturn(editedMovie);
        when(movieRepository.save(any(Movie.class))).thenReturn(mock(Movie.class));

        movieService.editMovie(USERNAME,
                MOVIE_WRONG_UUID,
                MOVIE_TITLE,
                MOVIE_NEW_DESCRIPTION,
                false);
    }

    @Test
    public void shouldDeleteMovie() throws Exception {

        Movie mockMovie = mock(Movie.class);

        when(movieRepository.findMovieById(MOVIE_UUID)).thenReturn(mockMovie);

        Boolean isDeleted = movieService.deleteMovie(USERNAME, MOVIE_UUID_TEXT);

        assertTrue(isDeleted);
        verify(movieRepository).delete(mockMovie);
    }

    @Test(expected = Exception.class)
    public void shouldNotDeleteMovieDueToWrongId() throws Exception {

        Movie mockMovie = mock(Movie.class);

        when(movieRepository.findMovieById(MOVIE_UUID)).thenReturn(mockMovie);

        movieService.deleteMovie(USERNAME, MOVIE_WRONG_UUID);
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
