package edu.upc.mishuserverapi.controller;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.upc.mishuserverapi.model.LeakedPassword;
import edu.upc.mishuserverapi.repositories.LeakedPasswordRepository;

@RestController
@RequestMapping("leakedpassword")
public class LeakController {
    @Autowired
    private LeakedPasswordRepository leakedPasswordRepository;

    /**
     * 导入社工库
     * @return 被导入的数据的hash
     */
    @PostMapping
    LeakedPassword add(String password){
        String hash=DigestUtils.sha1Hex(password);
        LeakedPassword leakedPassword=LeakedPassword.builder().hash(hash).shorthash(hash.substring(0, 5)).timestamp(new Timestamp(System.currentTimeMillis())).build();
        return leakedPasswordRepository.save(leakedPassword);
    }

    @GetMapping
    List<LeakedPassword> check(String shorthash){
        return leakedPasswordRepository.findByShorthashIgnoreCase(shorthash);
    }
}