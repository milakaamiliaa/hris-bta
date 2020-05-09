package bta.hris.repository;

import bta.hris.model.JawabanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JawabanDB extends JpaRepository<JawabanModel, Long> {
    List<JawabanModel> findAllBySoalIdSoal(Long idSoal);
}
