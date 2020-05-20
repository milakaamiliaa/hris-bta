package bta.hris.service;

import bta.hris.model.SubmittedJawabanModel;
import bta.hris.repository.SubmittedJawabanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SubmittedJawabanServiceImpl implements SubmittedJawabanService {

    @Autowired
    SubmittedJawabanDB submittedJawabanDB;

    @Override
    public SubmittedJawabanModel addSubmittedJawaban(SubmittedJawabanModel submittedJawaban) {
        submittedJawabanDB.save(submittedJawaban);

        return submittedJawaban;
    }
}
