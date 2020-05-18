package bta.hris.repository;

import bta.hris.model.SubmittedPaketSoalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmittedPaketSoalDB extends JpaRepository<SubmittedPaketSoalModel, Long> {
    
}
