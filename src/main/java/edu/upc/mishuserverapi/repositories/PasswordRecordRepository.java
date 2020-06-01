package edu.upc.mishuserverapi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import edu.upc.mishuserverapi.model.PasswordRecord;
import edu.upc.mishuserverapi.model.User;

public interface PasswordRecordRepository extends JpaRepository<PasswordRecord,Long>{
    List<PasswordRecord> findByUser(User user);

    PasswordRecord findByUserAndNameAndUsername(User user,String name,String username);

    @Modifying
    @Transactional
    void deleteByUserAndNameAndUsername(User user,String name,String username);
}