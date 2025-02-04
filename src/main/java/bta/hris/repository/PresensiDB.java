package bta.hris.repository;

import bta.hris.model.CabangModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PresensiDB extends JpaRepository<PresensiModel, Long> {
    Optional<PresensiModel> findById(Long id);

    List<PresensiModel> findAll();

    List<PresensiModel> findAllByPegawaiOrderByIdPresensiDesc(UserModel idUser);

    List<PresensiModel> findAllByCabangOrderByIdPresensiDesc(CabangModel cabang);

    List<PresensiModel> findByKodeGajiAndPegawai(String kodeGaji, UserModel idUser);

    List<PresensiModel> findByCabangAndStatus(CabangModel cabang, String status);

    List<PresensiModel> findByCabangAndPegawaiAndStatus(CabangModel cabang, UserModel user, String status);

}
