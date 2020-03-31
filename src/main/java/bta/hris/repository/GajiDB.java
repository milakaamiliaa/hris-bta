package bta.hris.repository;

import bta.hris.model.GajiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GajiDB extends JpaRepository<GajiModel, Long> {
    List<GajiModel> findAll();
}
