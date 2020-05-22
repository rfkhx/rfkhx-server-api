package edu.upc.mishuserverapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/{uid}/records")
public class PasswordRecordController {
    @GetMapping
    String getAll(@PathVariable Long uid){
        return "获取用户"+uid+"的全部记录";
    }
}