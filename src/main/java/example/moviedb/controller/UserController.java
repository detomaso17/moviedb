package example.moviedb.controller;

import example.moviedb.dto.RegisterUserDto;
import example.moviedb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/register")
    public void register(@RequestBody RegisterUserDto registerUserDto) throws Exception {

        userService.register(registerUserDto.getUsername(), registerUserDto.getPassword());
    }

    @PostMapping(value = "/delete")
    public void delete(@PathVariable String username) throws Exception {

        userService.delete(username);
    }
}
