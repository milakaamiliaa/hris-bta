package bta.hris.repository;

import bta.hris.model.CalonPengajarModel;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalonPengajarDB extends JpaRepository<CalonPengajarModel, String>{
    Optional<CalonPengajarModel> findByIdCalon(Long id);
    List<CalonPengajarModel> findAll();

}