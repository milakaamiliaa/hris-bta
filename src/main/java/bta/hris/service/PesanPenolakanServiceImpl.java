package bta.hris.service;

import bta.hris.model.PesanPenolakanModel;
import bta.hris.model.PresensiModel;
import bta.hris.model.UserModel;
import bta.hris.repository.PesanPenolakanDB;
import bta.hris.repository.UserDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PesanPenolakanServiceImpl implements PesanPenolakanService {

    @Autowired
    private PesanPenolakanDB pesanPenolakanDB;

    @Autowired
    private UserService userService;

    @Override
    public PesanPenolakanModel addPesanPenolakan(PesanPenolakanModel pesanPenolakan) {
//        pesanPenolakan.setCreatedAt(LocalDateTime.now());
        return pesanPenolakanDB.save(pesanPenolakan);
    }

    @Override
    public List<PesanPenolakanModel> getAllPesanPenolakanByPresensi(PresensiModel presensi) {
        return pesanPenolakanDB.findAllByPresensiOrderByCreatedAtAsc(presensi);
    }
}
