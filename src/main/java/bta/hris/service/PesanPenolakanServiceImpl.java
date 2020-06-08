package bta.hris.service;

import bta.hris.model.PesanPenolakanModel;
import bta.hris.model.PresensiModel;
import bta.hris.repository.PesanPenolakanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PesanPenolakanServiceImpl implements PesanPenolakanService {

    @Autowired
    private PesanPenolakanDB pesanPenolakanDB;

    @Override
    public PesanPenolakanModel addPesanPenolakan(PesanPenolakanModel pesanPenolakan) {
        return pesanPenolakanDB.save(pesanPenolakan);
    }

    @Override
    public List<PesanPenolakanModel> getAllPesanPenolakanByPresensi(PresensiModel presensi) {
        return pesanPenolakanDB.findAllByPresensiOrderByCreatedAtAsc(presensi);
    }
}
