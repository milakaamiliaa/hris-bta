package bta.hris.repository;

import bta.hris.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDB extends JpaRepository<UserModel, String> {
    Optional<UserModel> findByIdUser(String id);
    List<UserModel> findAll();
    UserModel findByNip(String nip);
}
