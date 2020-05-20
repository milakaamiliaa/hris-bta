package bta.hris.service;

import bta.hris.model.SubmittedSoalModel;
import bta.hris.repository.SubmittedSoalDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SubmittedSoalServiceImpl implements SubmittedSoalService {

    @Autowired
    SubmittedSoalDB submittedSoalDB;

    @Override
    public SubmittedSoalModel addSubmittedSoal(SubmittedSoalModel submittedSoal) {
        submittedSoalDB.save(submittedSoal);
        return submittedSoal;
    }
}
