package bta.hris.repository;

import bta.hris.model.SubmittedJawabanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmittedJawabanDB extends JpaRepository<SubmittedJawabanModel, Long> {

}
