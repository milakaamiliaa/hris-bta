package bta.hris.service;

import bta.hris.model.PaketSoalModel;
import bta.hris.model.SoalModel;
import bta.hris.repository.SoalDB;
import bta.hris.repository.PaketSoalDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SoalServiceImpl implements SoalService {
    @Autowired
    private SoalDB soalDB;

    @Autowired
    private PaketSoalDB paketSoalDB;

    @Override
    public List<SoalModel> getAllSoalByNamaPaketSoal(String nama) {
        return soalDB.findAllByPaketSoal_Nama(nama);
    }

    @Override
    public List<SoalModel> getAllSoalByIdPaketSoal(Long idPaket) {
        PaketSoalModel paket = paketSoalDB.findByIdPaket(idPaket);
        return soalDB.findByPaketSoal(paket);
    }

    @Override
    public List<SoalModel> getSoalByPaketSoal(PaketSoalModel paketSoal) {
        List<SoalModel> soal = soalDB.findAllByPaketSoalOrderByIdSoalDesc(paketSoal);
        soal.removeIf(n -> !n.getIsActive());
        return soal;
    }

    @Override
    public SoalModel addSoal(SoalModel soal) {
        soalDB.save(soal);
        return soal;
    }

    @Override
    public SoalModel getSoalById(Long idSoal) {
        return soalDB.findById(idSoal).get();
    }

    @Override
    public SoalModel editSoal(SoalModel soal) {
        SoalModel targetSoal = soalDB.findById(soal.getIdSoal()).get();

        try {
            targetSoal.setActive(soal.getIsActive());
            targetSoal.setPaketSoal(soal.getPaketSoal());
            targetSoal.setListJawaban(soal.getListJawaban());
            targetSoal.setPertanyaan(soal.getPertanyaan());

            soalDB.save(targetSoal);

            return targetSoal;

        } catch (NullPointerException nullExeption) {
            return null;
        }
    }

    @Override
    public SoalModel deleteSoal(SoalModel soal) {
        SoalModel targetSoal = soalDB.findById(soal.getIdSoal()).get();

        if (targetSoal.getIsActive()) {
            targetSoal.setActive(false);
        }

        return soalDB.save(targetSoal);
    }
}
