package bta.hris.repository;

import bta.hris.model.SoalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoalDB extends JpaRepository<SoalModel, Long> {
}
