package bta.hris.service;

import bta.hris.model.JawabanModel;
import bta.hris.model.SoalModel;

import java.util.List;

public interface JawabanService {
    List<JawabanModel> getAllJawabanBySoal(SoalModel soal);
    JawabanModel addJawaban(JawabanModel jawaban);
    JawabanModel getJawabanById(Long idJawaban);
    JawabanModel editJawaban(JawabanModel jawaban);
    JawabanModel deleteJawaban(JawabanModel jawaban);
}
