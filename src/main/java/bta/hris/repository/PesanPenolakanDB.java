package bta.hris.repository;

import bta.hris.model.PesanPenolakanModel;
import bta.hris.model.PresensiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PesanPenolakanDB extends JpaRepository<PesanPenolakanModel, Long> {
    Optional<PesanPenolakanModel> findByIdPesanPenolakan(Long idPesanPenolakan);

    List<PesanPenolakanModel> findAllByPresensiOrderByCreatedAtAsc(PresensiModel presensi);
}
