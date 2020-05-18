package bta.hris.service;

import bta.hris.model.SubmittedPaketSoalModel;
import bta.hris.repository.SubmittedPaketSoalDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SubmittedPaketSoalServiceImpl implements SubmittedPaketSoalService {

    @Autowired
    SubmittedPaketSoalDB submittedPaketSoalDB;

    @Override
    public SubmittedPaketSoalModel addSubmittedPaketSoal(SubmittedPaketSoalModel submittedPaketSoal) {
        submittedPaketSoalDB.save(submittedPaketSoal);

        return submittedPaketSoal;
    }

}
