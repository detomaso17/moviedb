package example.moviedb.service;

import example.moviedb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public void register(String username, String password) throws Exception {

        //TODO implement


    }

    public void delete(String username) throws Exception {

        //TODO implement
    }

}
