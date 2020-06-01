package edu.upc.mishuserverapi.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import edu.upc.mishuserverapi.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    public User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException {
        if (emailExists(accountDto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getUsername());
        }
        final User user = new User();

        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getUsername());
        // user.setUsing2FA(accountDto.isUsing2FA());
        user.setRoles(Arrays.asList(createRoleIfNotFound("ROLE_USER",Arrays.asList(createPrivilegeIfNotFound("UPDATE_PRIVILEGE")))));
        user.setEnabled(true);
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

}