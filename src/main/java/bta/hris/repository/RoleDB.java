package bta.hris.repository;

import bta.hris.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleDB extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel> findById(Long id);
    List<RoleModel> findAll();
}

