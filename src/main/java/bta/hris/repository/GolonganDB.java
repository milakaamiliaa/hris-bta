package bta.hris.repository;

import bta.hris.model.GolonganModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GolonganDB extends JpaRepository<GolonganModel, Long> {

}
