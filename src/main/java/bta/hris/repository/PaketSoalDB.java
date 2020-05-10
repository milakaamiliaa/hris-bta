package bta.hris.repository;

import bta.hris.model.PaketSoalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaketSoalDB extends JpaRepository<PaketSoalModel, Long> {
    PaketSoalModel findByIdPaket(Long idPaket);
    PaketSoalModel findByNama(String nama);
}
