package bta.hris.repository;

import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresensiDB extends JpaRepository<PresensiModel, Long> {
    List<PresensiModel> findAllByPegawai(UserModel idUser);

}
