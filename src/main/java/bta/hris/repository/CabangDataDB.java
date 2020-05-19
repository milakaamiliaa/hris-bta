package bta.hris.repository;

import bta.hris.model.CabangDataModel;
import bta.hris.model.CabangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabangDataDB extends JpaRepository<CabangDataModel, Long> {
    CabangDataModel findByCabangAndPeriode(CabangModel cabang, String periode);
}
