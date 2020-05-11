package bta.hris.repository;

import bta.hris.model.PaketSoalModel;
import bta.hris.model.SoalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoalDB extends JpaRepository<SoalModel, Long> {
    List<SoalModel> findAllByPaketSoal(PaketSoalModel paketSoal);
}
