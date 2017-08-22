package example.moviedb.service;

import example.moviedb.model.User;
import example.moviedb.repository.MovieRepository;
import example.moviedb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, MovieRepository movieRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(String username, String password) throws Exception {

        User user = userRepository.findByUsername(username);
        if (user != null) {
            throw new Exception("There is already a user with username: " + username);
        }
        User newUser = new User(username, passwordEncoder.encode(password));
        userRepository.save(newUser);

    }

    @Transactional
    public void delete(String username) throws Exception {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new Exception("There is no user with username: " + username);
        }
        movieRepository.deleteByUser_Username(username);
        userRepository.delete(user);
    }

}
