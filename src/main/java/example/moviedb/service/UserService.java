package example.moviedb.service;

import example.moviedb.model.User;
import example.moviedb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public void register(String username, String password) throws Exception {

        User user = userRepository.findByUsername(username);
        if (user != null) {
            throw new Exception("There is already a user with username: " + username);
        }
        User newUser = new User(username, password, true);
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
