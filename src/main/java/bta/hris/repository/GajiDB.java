package bta.hris.repository;

import bta.hris.model.GajiModel;
import bta.hris.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GajiDB extends JpaRepository<GajiModel, Long> {
    Optional<GajiModel> findById(Long id);
    List<GajiModel> findAll();
    List<GajiModel> findAllByPegawai(UserModel idUser);
}
