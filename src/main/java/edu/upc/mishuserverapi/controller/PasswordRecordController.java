package edu.upc.mishuserverapi.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.upc.mishuserverapi.annotation.LimitAccess;
import edu.upc.mishuserverapi.dto.PasswordRecordDto;
import edu.upc.mishuserverapi.model.PasswordRecord;
import edu.upc.mishuserverapi.model.User;
import edu.upc.mishuserverapi.repositories.PasswordRecordRepository;
import edu.upc.mishuserverapi.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/records")
@Slf4j
public class PasswordRecordController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRecordRepository passwordRecordRepository;

    @GetMapping
    @LimitAccess(frequency = 10, millisecond = 1000)
    List<PasswordRecord> getAll(Principal principal){
        log.info("GET");
        User user = userRepository.findByEmail(principal.getName());
        return passwordRecordRepository.findByUser(user);
    }

    @PostMapping
    @LimitAccess(frequency = 500, millisecond = 1000)
    PasswordRecord create(PasswordRecordDto passwordRecordDto,Principal principal){
        User user=userRepository.findByEmail(principal.getName());
        PasswordRecord passwordRecord=passwordRecordRepository.findByUserAndNameAndUsername(user, passwordRecordDto.getName(), passwordRecordDto.getUsername());
        if(passwordRecord==null){
            passwordRecord=new PasswordRecord();
        }
        passwordRecord.setName(passwordRecordDto.getName());
        passwordRecord.setNote(passwordRecordDto.getNote());
        passwordRecord.setPassword(passwordRecordDto.getPassword());
        passwordRecord.setSynctimestamp(new Timestamp(System.currentTimeMillis()));
        passwordRecord.setType(passwordRecordDto.getType());
        passwordRecord.setUrl(passwordRecordDto.getUrl());
        passwordRecord.setUser(user);
        passwordRecord.setUsername(passwordRecordDto.getUsername());
        return passwordRecordRepository.saveAndFlush(passwordRecord);
    }

    @DeleteMapping
    void delete(@RequestParam(required = false) String name,@RequestParam(required = false) String username,Principal principal,HttpServletResponse response){
        User user=userRepository.findByEmail(principal.getName());
        if(name==null&&username==null){
            passwordRecordRepository.deleteAll();
        }else{
            passwordRecordRepository.deleteByUserAndNameAndUsername(user, name, username);
        }
    }
}