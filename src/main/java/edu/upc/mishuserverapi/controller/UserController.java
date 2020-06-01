package edu.upc.mishuserverapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.upc.mishuserverapi.annotation.LimitAccess;
import edu.upc.mishuserverapi.dto.UserDto;
import edu.upc.mishuserverapi.model.User;
import edu.upc.mishuserverapi.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @LimitAccess(frequency = 1, millisecond = 60000)
    User register(UserDto userDto){
        return userService.registerNewUserAccount(userDto);
    }
}