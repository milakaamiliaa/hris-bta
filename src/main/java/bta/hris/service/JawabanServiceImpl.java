package bta.hris.service;

import bta.hris.model.JawabanModel;
import bta.hris.repository.JawabanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class JawabanServiceImpl implements JawabanService{
    @Autowired
    private JawabanDB jawabanDB;

    @Override
    public List<JawabanModel> getAllJawabanByIdSoal(Long idSoal){
        return jawabanDB.findAllBySoalIdSoal(idSoal);
    }

    @Override
    public JawabanModel getJawabanById(Long idJawaban){
        return jawabanDB.findByIdJawaban(idJawaban);
    }
}
