package bta.hris.service;

import bta.hris.model.PaketSoalModel;
import bta.hris.model.SoalModel;
import bta.hris.repository.SoalDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoalServiceImpl implements SoalService {

    @Autowired
    private SoalDB soalDB;

    @Override
    public List<SoalModel> getSoalByPaketSoal(PaketSoalModel paketSoal) {
        return soalDB.findAllByPaketSoalOrderByIdSoalDesc(paketSoal);
    }

    @Override
    public SoalModel addSoal(SoalModel soal) {
        soalDB.save(soal);
        return soal;
    }
}
