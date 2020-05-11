package bta.hris.service;

import bta.hris.model.JawabanModel;
import bta.hris.model.SoalModel;
import bta.hris.repository.JawabanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JawabanServiceImpl implements JawabanService{

    @Autowired
    private JawabanDB jawabanDB;

    @Override
    public List<JawabanModel> getAllJawabanBySoal(SoalModel soal) {
        return jawabanDB.findAllBySoal(soal);
    }
}
