package edu.upc.mishuserverapi.service;

import java.util.Collection;
import java.util.Optional;

import edu.upc.mishuserverapi.dto.UserDto;
import edu.upc.mishuserverapi.error.UserAlreadyExistException;
import edu.upc.mishuserverapi.model.Privilege;
import edu.upc.mishuserverapi.model.Role;
import edu.upc.mishuserverapi.model.User;


public interface UserService {

    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;
    
    Privilege createPrivilegeIfNotFound(String name);

    Role createRoleIfNotFound(String name, Collection<Privilege> privileges);

}
