package edu.upc.mishuserverapi.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.upc.mishuserverapi.dto.UserDto;
import edu.upc.mishuserverapi.error.UserAlreadyExistException;
import edu.upc.mishuserverapi.model.Privilege;
import edu.upc.mishuserverapi.model.Role;
import edu.upc.mishuserverapi.model.User;
import edu.upc.mishuserverapi.repositories.PrivilegeRepository;
import edu.upc.mishuserverapi.repositories.RoleRepository;
import edu.upc.mishuserverapi.repositories.UserRepository;
import edu.upc.mishuserverapi.service.MailService;
import edu.upc.mishuserverapi.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private MailService mailService;
    @Value("${app.apipath}")
    private String apiPath;

    @Override
    public User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException {
        if (emailExists(accountDto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getUsername());
        }
        final User user = new User();

        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getUsername());
        user.setEnabled(true);
        String verifyString=RandomStringUtils.randomAlphabetic(15);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        ops.set("verify"+user.getEmail(), verifyString,15,TimeUnit.MINUTES);
        log.info("用户{}注册，验证字符串为{}",user.getEmail(),verifyString);

        //发送邮件
        mailService.sendHtmlMail(user.getEmail(),"欢迎注册《密书》帐号服务","<h1>你可以点击下方链接验证帐户。</h1><a href='"+apiPath+"api/user/verify?email="+user.getEmail()+"&token="+verifyString+"'>点击链接验证帐户</a>");

        return userRepository.save(user);
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Override
    public Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Override
    public boolean verifyEmail(String email, String token) {
        log.info("尝试使用token（{}）验证邮箱{}",token,email);
        User user=userRepository.findByEmail(email);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        Object k1 = ops.get("verify"+user.getEmail());
        if(k1!=null&&k1.toString().equals(token)){
            user.setRoles(new ArrayList<>(Arrays.asList(createRoleIfNotFound("ROLE_USER",Arrays.asList(createPrivilegeIfNotFound("UPDATE_PRIVILEGE"))))));
            userRepository.save(user);
            return true;
        }
        return false;
    }

}