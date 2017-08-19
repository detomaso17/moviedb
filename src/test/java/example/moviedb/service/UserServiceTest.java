package example.moviedb.service;

import example.moviedb.model.User;
import example.moviedb.repository.UserRepository;
import jdk.nashorn.internal.runtime.ECMAException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @Before
    public void setUp() throws Exception {

        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void register() throws Exception {

        when(userRepository.findByUsername(any(String.class))).thenReturn(null);
        when(passwordEncoder.encode(any(String.class))).thenReturn(anyString());
        userService.register("newUser", "newPassword");

        verify(userRepository).findByUsername("newUser");

    }

    @Test(expected = Exception.class)
    public void notRegister() throws Exception {

        User mockUser = mock(User.class);

        when(userRepository.findByUsername("newUser")).thenReturn(mockUser);

        userService.register("newUser", "newPassword");

    }

    @Test
    public void delete() throws Exception {

        User mockUser = mock(User.class);

        when(userRepository.findByUsername("newUser")).thenReturn(mockUser);

        userService.delete("newUser");

        verify(userRepository).findByUsername("newUser");
        verify(userRepository).delete(mockUser);
    }

    @Test(expected = Exception.class)
    public void notDelete() throws Exception {

        when(userRepository.findByUsername(any(String.class))).thenReturn(null);

        userService.delete("newUser");

    }

}
