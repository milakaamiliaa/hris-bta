package bta.hris.service;

import bta.hris.model.JawabanModel;
import bta.hris.model.SoalModel;
import bta.hris.repository.JawabanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JawabanServiceImpl implements JawabanService {

    @Autowired
    private JawabanDB jawabanDB;

    @Override
    public List<JawabanModel> getAllJawabanBySoal(SoalModel soal) {
        List<JawabanModel> jawaban =  jawabanDB.findAllBySoal(soal);
        jawaban.removeIf(n -> !n.isActive());

        return jawaban;
    }

    @Override
    public JawabanModel addJawaban(JawabanModel jawaban) {
        jawabanDB.save(jawaban);
        return jawaban;
    }

    @Override
    public JawabanModel getJawabanById(Long idJawaban) {
        return jawabanDB.findById(idJawaban).get();
    }

    @Override
    public JawabanModel editJawaban(JawabanModel jawaban) {
        JawabanModel targetJawaban = jawabanDB.findById(jawaban.getIdJawaban()).get();

        try {
            targetJawaban.setActive(jawaban.isActive());
            targetJawaban.setSoal(jawaban.getSoal());
            targetJawaban.setJawaban(jawaban.getJawaban());

            jawabanDB.save(targetJawaban);

            return targetJawaban;

        } catch (NullPointerException nullExeption) {
            return null;
        }
    }

    @Override
    public JawabanModel deleteJawaban(JawabanModel jawaban) {
        JawabanModel targetJawaban = jawabanDB.findById(jawaban.getIdJawaban()).get();

        if (targetJawaban.isActive()) {
            targetJawaban.setActive(false);
        }

        return jawabanDB.save(targetJawaban);
    }
}
