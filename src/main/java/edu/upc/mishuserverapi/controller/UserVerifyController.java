package edu.upc.mishuserverapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.upc.mishuserverapi.service.UserService;

@Controller
@RequestMapping("/user/verify")
public class UserVerifyController {
    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseBody
    String verify(@RequestParam String email,@RequestParam String token){
        if(userService.verifyEmail(email, token)){
            return "success";
        }else{
            return "fail";
        }
    }
}