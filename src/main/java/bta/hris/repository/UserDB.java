package bta.hris.repository;

import bta.hris.model.UserModel;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserDB extends JpaRepository<UserModel, Long> {
    UserModel findByNip(String nip);
}
