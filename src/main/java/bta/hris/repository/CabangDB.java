package bta.hris.repository;

import bta.hris.model.CabangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CabangDB extends JpaRepository<CabangModel, Long> {
    Optional<CabangModel> findByIdCabang(Long idCabang);

}
