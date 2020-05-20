package bta.hris.repository;

import bta.hris.model.CabangDataModel;
import bta.hris.model.CabangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Repository
public interface CabangDataDB extends JpaRepository<CabangDataModel, Long> {
    CabangDataModel findByCabangAndPeriode(CabangModel cabang, String periode);
    List<CabangDataModel> findAllByCabangAndCreatedAtBetweenOrderByCreatedAtAsc(CabangModel cabang, LocalDate start, LocalDate end);
    List<CabangDataModel> findByCabang(CabangModel cabang);
}
