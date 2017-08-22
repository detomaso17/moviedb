package example.moviedb.service;

import example.moviedb.model.User;
import example.moviedb.repository.MovieRepository;
import example.moviedb.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String USERNAME = "newUser";
    private static final String PASSWORD = "newPassword";


    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @Before
    public void setUp() throws Exception {

        userService = new UserService(userRepository, movieRepository, passwordEncoder);
    }

    @Test
    public void register() throws Exception {

        when(userRepository.findByUsername(any(String.class))).thenReturn(null);
        when(passwordEncoder.encode(any(String.class))).thenReturn(anyString());
        userService.register(USERNAME, PASSWORD);

        verify(userRepository).findByUsername(USERNAME);
    }

    @Test(expected = Exception.class)
    public void notRegister() throws Exception {

        User mockUser = mock(User.class);

        when(userRepository.findByUsername(USERNAME)).thenReturn(mockUser);

        userService.register(USERNAME, PASSWORD);
    }

    @Test
    public void delete() throws Exception {

        User mockUser = mock(User.class);

        when(userRepository.findByUsername(USERNAME)).thenReturn(mockUser);

        userService.delete(USERNAME);

        verify(userRepository).findByUsername(USERNAME);
        verify(userRepository).delete(mockUser);
        verify(movieRepository).deleteByUser_Username(USERNAME);
    }

    @Test(expected = Exception.class)
    public void notDelete() throws Exception {

        when(userRepository.findByUsername(any(String.class))).thenReturn(null);

        userService.delete(USERNAME);

    }

}
