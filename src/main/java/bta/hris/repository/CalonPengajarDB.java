package bta.hris.repository;

import bta.hris.model.CalonPengajarModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalonPengajarDB extends JpaRepository<CalonPengajarModel, String> {
    Optional<CalonPengajarModel> findByIdCalon(String id);

    List<CalonPengajarModel> findAll();

    Optional<CalonPengajarModel> findByUsername(String username);

}
