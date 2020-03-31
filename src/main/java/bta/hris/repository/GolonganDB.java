package bta.hris.repository;

import bta.hris.model.GolonganModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GolonganDB extends JpaRepository<GolonganModel, Long> {
    Optional<GolonganModel> findById(Long id);
    List<GolonganModel> findAll();
}
