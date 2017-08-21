package example.moviedb.service;

import example.moviedb.model.User;
import example.moviedb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
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

    public void delete(String username) throws Exception {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new Exception("There is no user with username: " + username);
        }
        userRepository.delete(user);
    }

}
