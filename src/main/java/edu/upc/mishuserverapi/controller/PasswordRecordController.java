package edu.upc.mishuserverapi.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.sun.tools.sjavac.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.upc.mishuserverapi.dto.PasswordRecordDto;
import edu.upc.mishuserverapi.model.PasswordRecord;
import edu.upc.mishuserverapi.model.User;
import edu.upc.mishuserverapi.repositories.PasswordRecordRepository;
import edu.upc.mishuserverapi.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user/{uid}/records")
@Slf4j
public class PasswordRecordController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRecordRepository passwordRecordRepository;

    @GetMapping
    List<PasswordRecord> getAll(@PathVariable Long uid){
        log.info("GET");
        Optional<User> user=userRepository.findById(uid);
        if(user.isEmpty()){
            log.warn("未找到用户{}",uid);
            return new ArrayList<>();
        }
        return passwordRecordRepository.findByUser(user.get());
    }

    @PostMapping
    PasswordRecord create(@PathVariable Long uid,PasswordRecordDto passwordRecordDto){
        Optional<User> user=userRepository.findById(uid);
        log.info("提交的请求：{}",passwordRecordDto);
        if(user.isEmpty()){
            log.warn("未找到用户{}",uid);
            return null;
        }
        PasswordRecord passwordRecord=passwordRecordRepository.findByUserAndNameAndUsername(user.get(), passwordRecordDto.getName(), passwordRecordDto.getUsername());
        if(passwordRecord==null){
            passwordRecord=new PasswordRecord();
        }
        passwordRecord.setName(passwordRecordDto.getName());
        passwordRecord.setNote(passwordRecordDto.getNote());
        passwordRecord.setPassword(passwordRecord.getPassword());
        passwordRecord.setSynctimestamp(new Timestamp(System.currentTimeMillis()));
        passwordRecord.setType(passwordRecordDto.getType());
        passwordRecord.setUrl(passwordRecordDto.getUrl());
        passwordRecord.setUser(user.get());
        passwordRecord.setUsername(passwordRecordDto.getUsername());
        return passwordRecordRepository.saveAndFlush(passwordRecord);
    }
}