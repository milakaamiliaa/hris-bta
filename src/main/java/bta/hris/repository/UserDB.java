package bta.hris.repository;

import bta.hris.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDB extends JpaRepository<UserModel, Long> {
    UserModel findByNip(String nip);
}
