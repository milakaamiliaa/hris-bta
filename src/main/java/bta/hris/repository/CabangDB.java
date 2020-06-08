package bta.hris.repository;

import bta.hris.model.CabangModel;
import bta.hris.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CabangDB extends JpaRepository<CabangModel, Long> {
    Optional<CabangModel> findByIdCabang(Long idCabang);

    Optional<CabangModel> findByStafCabang(UserModel stafCabang);

    List<CabangModel> findAllByOrderByIdCabangDesc();
}
