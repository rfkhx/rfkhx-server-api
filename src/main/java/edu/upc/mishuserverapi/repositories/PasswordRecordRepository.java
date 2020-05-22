package edu.upc.mishuserverapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.upc.mishuserverapi.model.PasswordRecord;
import edu.upc.mishuserverapi.model.User;

public interface PasswordRecordRepository extends JpaRepository<PasswordRecord,Long>{
    List<PasswordRecord> findByUser(User user);

    PasswordRecord findByUserAndNameAndUsername(User user,String name,String username);
}