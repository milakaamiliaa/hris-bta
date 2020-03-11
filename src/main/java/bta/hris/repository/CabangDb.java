package bta.hris.repository;

import bta.hris.model.CabangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabangDb extends JpaRepository<CabangModel, Long> {


}
