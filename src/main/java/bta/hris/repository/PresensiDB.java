package bta.hris.repository;

import bta.hris.model.PresensiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PresensiDB extends JpaRepository<PresensiModel, Long> {
    Optional<PresensiModel> findById(Long id);
    List<PresensiModel> findAll();
}
