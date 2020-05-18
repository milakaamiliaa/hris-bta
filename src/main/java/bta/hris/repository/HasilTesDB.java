package bta.hris.repository;

import bta.hris.model.HasilTesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HasilTesDB extends JpaRepository<HasilTesModel, Long> {
}
