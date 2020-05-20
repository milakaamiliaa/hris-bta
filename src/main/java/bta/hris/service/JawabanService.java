package bta.hris.service;

import bta.hris.model.JawabanModel;
import bta.hris.model.SoalModel;

import java.util.List;

public interface JawabanService {
    List<JawabanModel> getAllJawabanByIdSoal(Long idSoal);
    JawabanModel getJawabanById(Long idJawaban);
    List<JawabanModel> getAllJawabanBySoal(SoalModel soal);
    JawabanModel addJawaban(JawabanModel jawaban);
    JawabanModel editJawaban(JawabanModel jawaban);
    JawabanModel deleteJawaban(JawabanModel jawaban);
}
