package bta.hris.service;

import bta.hris.model.SoalModel;
import bta.hris.repository.SoalDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoalServiceImpl implements SoalService {

    @Autowired
    private SoalDB soalDB;

    public SoalModel addSoal(SoalModel soal) {
        soalDB.save(soal);
        return soal;
    }

    public List<SoalModel> getAllSoal() {
        return soalDB.findAll();
    }
}
